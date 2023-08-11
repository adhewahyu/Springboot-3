package com.dan.taskservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut( "within(com.dan.taskservice.controller..*)" +
            " || within(com.dan.taskservice.service..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isInfoEnabled()) {
            log.info("{}.{}() - called", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        }
        Object result = joinPoint.proceed();
        if (log.isInfoEnabled()) {
            log.info("{}.{}() - end", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        }
        return result;
    }

}
