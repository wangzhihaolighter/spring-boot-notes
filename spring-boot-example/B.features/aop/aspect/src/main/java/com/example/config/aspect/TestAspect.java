package com.example.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class TestAspect {

  // 切入点

  @Pointcut("execution(public * com.example.controller..*.*(..))")
  public void pointcut() {
    // pointcut
  }

  // 通过连接点切入

  @Before("pointcut()")
  public void doBefore(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    log.info("[{}] doBefore", methodName);
  }

  @AfterReturning(pointcut = "pointcut()", returning = "response")
  public void doAfterReturning(JoinPoint joinPoint, Object response) {
    String methodName = joinPoint.getSignature().getName();
    log.info("[{}] doAfterReturning - RESPONSE: {}", methodName, response);
  }

  @After("pointcut()")
  public void doAfter(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    log.info("[{}] doAfter", methodName);
  }

  @Around("pointcut()")
  public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
    String methodName = joinPoint.getSignature().getName();
    log.info("[{}] doAround pre", methodName);
    Object proceed = joinPoint.proceed();
    log.info("[{}] doAround post", methodName);
    return proceed;
  }
}
