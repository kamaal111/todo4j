package io.kamaal.todo4j.shared.aspects;

import io.kamaal.todo4j.shared.exception.BaseHTTPException;
import io.kamaal.todo4j.shared.model.ErrorResponse;
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

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class ExceptionHandlingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingAspect.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    // Change from @AfterThrowing to @Around for better control of the execution flow
    @Around("controllerMethods()")
    public Object handleControllerExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        Exception exception;
        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            exception = ex;
        }

        logger.error("Exception in controller: {}", exception.getMessage());

        var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return null;

        var response = attributes.getResponse();
        if (response == null) return null;
        if (response.isCommitted()) return null;

        ErrorResponse errorResponse;
        if (exception instanceof BaseHTTPException baseHTTPException) {
            response.setStatus(baseHTTPException.getStatus().value());
            errorResponse = ErrorResponse.fromHTTPStatus(baseHTTPException.getStatus(), baseHTTPException.getMessage());
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse = ErrorResponse.fromHTTPStatus(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }

        writeErrorResponse(response, errorResponse);
        logger.debug("Handled exception with response status: {} and body: {}", response.getStatus(), errorResponse);

        return null;
    }
    
    private void writeErrorResponse(HttpServletResponse response, ErrorResponse errorResponse) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        var responseBytes = objectMapper.writeValueAsBytes(errorResponse);
        response.setContentLength(responseBytes.length);

        response.getOutputStream().write(responseBytes);
        response.getOutputStream().flush();
    }
}