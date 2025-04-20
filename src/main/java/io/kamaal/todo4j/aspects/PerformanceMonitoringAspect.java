package io.kamaal.todo4j.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMonitoringAspect {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitoringAspect.class);
    
    // Point cut for all controller methods
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}
    
    @Around("controllerMethods()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        var start = System.nanoTime();
        
        try {
            return joinPoint.proceed();
        } finally {
            var finish = System.nanoTime();
            var timeElapsed = finish - start;
            
            logger.info("Performance: {}.{} completed in {} ms", 
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                timeElapsed / 1_000_000
            );
            
            if (timeElapsed > 100_000_000) { // 100ms threshold
                logger.warn("Slow API call detected: {}.{} took {} ms", 
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    timeElapsed / 1_000_000
                );
            }
        }
    }
}