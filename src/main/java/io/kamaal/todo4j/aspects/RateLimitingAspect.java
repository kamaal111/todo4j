package io.kamaal.todo4j.aspects;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kamaal.todo4j.shared.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class RateLimitingAspect {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitingAspect.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    // Configure rate limits: max 10 requests per minute per IP
    private static final int MAX_REQUESTS_PER_MINUTE = 10;
    private static final long ONE_MINUTE = 60 * 1000;

    // Track request counts per IP address with timestamps for cleanup
    private final Map<String, RequestCount> requestCounts = new ConcurrentHashMap<>();

    private static class RequestCount {
        final AtomicInteger count = new AtomicInteger(0);
        long resetTime;
        
        RequestCount() {
            this.resetTime = System.currentTimeMillis() + ONE_MINUTE;
        }
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object limitRequestRate(ProceedingJoinPoint joinPoint) throws Throwable {
        var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String clientIP = getClientIP(request);
            
            // Get or create request count for this IP
            RequestCount requestCount = requestCounts.computeIfAbsent(clientIP, k -> new RequestCount());
            
            // Reset counter if time window has passed
            long now = System.currentTimeMillis();
            if (now > requestCount.resetTime) {
                requestCount.count.set(0);
                requestCount.resetTime = now + ONE_MINUTE;
            }
            
            // Check if rate limit is exceeded
            int currentCount = requestCount.count.incrementAndGet();
            if (currentCount > MAX_REQUESTS_PER_MINUTE) {
                logger.warn("Rate limit exceeded for IP: {}", clientIP);
                
                var response = attributes.getResponse();
                if (response != null) {
                    var errorResponse = new ErrorResponse(
                        (long) HttpStatus.TOO_MANY_REQUESTS.value(),
                        HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(),
                "Rate limit exceeded. Try again in a minute."
                    );
                    var errorResponseBytes = objectMapper.writeValueAsBytes(errorResponse);
                    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("X-RateLimit-Limit", String.valueOf(MAX_REQUESTS_PER_MINUTE));
                    response.setHeader("X-RateLimit-Remaining", "0");
                    response.setHeader("X-RateLimit-Reset", String.valueOf(requestCount.resetTime));
                    response.setContentLength(errorResponseBytes.length);
                    response.getOutputStream().write(errorResponseBytes);
                    response.getOutputStream().flush();
                }
                
                return null;
            }
            
            // Add rate limit headers to response
            var response = attributes.getResponse();
            if (response != null) {
                response.setHeader("X-RateLimit-Limit", String.valueOf(MAX_REQUESTS_PER_MINUTE));
                response.setHeader("X-RateLimit-Remaining", String.valueOf(MAX_REQUESTS_PER_MINUTE - currentCount));
                response.setHeader("X-RateLimit-Reset", String.valueOf(requestCount.resetTime));
            }
        }
        
        // Clean up expired entries occasionally
        if (Math.random() < 0.01) {
            cleanupExpiredEntries();
        }
        
        return joinPoint.proceed();
    }
    
    private String getClientIP(HttpServletRequest request) {
        var xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }
    
    private void cleanupExpiredEntries() {
        var now = System.currentTimeMillis();
        requestCounts.entrySet().removeIf(entry -> entry.getValue().resetTime < now);
    }
}