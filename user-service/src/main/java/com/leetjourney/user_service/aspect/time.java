package com.leetjourney.user_service.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class time {

    @Pointcut("execution(* com.leetjourney.user_service.controller.*.*(..))")
    public void controllerMethods(){}

    @Around("controllerMethods()")

    public Object measureExecutionTime(ProceedingJoinPoint pjp) throws Throwable{
        Long start = System.nanoTime();
        try{
            return pjp.proceed();
        }
        finally {
            Long end = System.nanoTime();
            Long elapsedTimeNs = end - start;
            Long elapsedTimeMs = TimeUnit.NANOSECONDS.toMillis(elapsedTimeNs);
            String signature = pjp.getSignature().toShortString();
            log.info("Controller method called: {}, took total time: {}",signature, elapsedTimeMs);
        }
    }
}
