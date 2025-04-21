package io.kamaal.todo4j.shared.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    @Pointcut("within(io.kamaal.todo4j.user.service..*) || within(io.kamaal.todo4j.greeting.service..*)")
    public void serviceMethods() {}
    
    @Around("serviceMethods()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        var logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        var methodName = joinPoint.getSignature().getName();
        var className = joinPoint.getTarget().getClass().getSimpleName();

        logger.info("Entering {}::{} with parameters: {}", className, methodName, Arrays.toString(joinPoint.getArgs()));
        
        var startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
            var endTime = System.currentTimeMillis();
            logger.info(
                    "Exiting {}::{} with result: {}. Execution time: {} ms",
                    className,
                    methodName,
                    result,
                    (endTime - startTime)
            );
            
            return result;
        } catch (Exception e) {
            logger.error("Exception in {}::{} with message: {}", className, methodName, e.getMessage());

            throw e;
        }
    }
}