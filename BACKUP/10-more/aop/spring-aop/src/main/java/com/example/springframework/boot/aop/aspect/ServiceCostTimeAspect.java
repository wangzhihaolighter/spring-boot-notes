package com.example.springframework.boot.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

/**
 * 服务用时切面，记录服务用时
 */
@Slf4j
@Order(3)
@Aspect
public class ServiceCostTimeAspect {

    /**
     * 切入点 - service层
     */
    @Pointcut("execution(public * com.example.springframework.boot.aop.service..*.*(..))")
    public void serviceCostTime() {
    }

    /**
     * 通过连接点切入 - Around，记录用时
     */
    @Around("serviceCostTime()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        //start time
        long startTime = System.currentTimeMillis();
        Object proceed = point.proceed();
        String name = point.getSignature().getName();
        long endTime = System.currentTimeMillis();
        log.info("[SERVICE] service(" + name + ") cost time (ms) : " + (endTime - startTime));
        return proceed;
    }

}
