package com.leetjourney.device_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.leetjourney.device_service.service.*.*(**))")
    public void deviceServiceMethods(){}

    @Before("deviceServiceMethods()")
    public void logBeforeMethodCall(JoinPoint joinPoint){
        log.info("Called [device_service] service method: {}, with argument: {}",
                joinPoint.getSignature().getName(),joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "deviceServiceMethods()", returning = "result")
    public void logAfterMethodCall(JoinPoint jointPoint, Object result){
        log.info("Called [device_service] service method: {}, returned: {}",
                jointPoint.getSignature().getName(),result);
    }
}
