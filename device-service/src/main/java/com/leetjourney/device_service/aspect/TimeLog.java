package com.leetjourney.device_service.aspect;


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
public class TimeLog {

    @Pointcut("execution(* com.leetjourney.device_service.controller.*.*(**))")
    public void deviceServiceControllers(){}


    @Around("deviceServiceControllers()")
    public Object measureExecutionTime(ProceedingJoinPoint pjp) throws  Throwable{
        Long start = System.nanoTime();
        try{
            return pjp.proceed();
        }
        finally {
            Long end = System.nanoTime();
            Long timeElapsed = end-start;
            Long timeElapsedms = TimeUnit.NANOSECONDS.toMillis(timeElapsed);
            String signature = pjp.getSignature().getName();
            log.info("Controller method called: {}, took total time: {}ms",signature,timeElapsedms);
        }
    }
}
