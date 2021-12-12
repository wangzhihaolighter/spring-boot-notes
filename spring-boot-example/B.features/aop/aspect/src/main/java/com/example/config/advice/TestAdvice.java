package com.example.config.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TestAdvice implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    log.info("[{}] Advice invoke pre", methodInvocation.getMethod().getName());
    Object proceed = methodInvocation.proceed();
    log.info("[{}] Advice invoke post", methodInvocation.getMethod().getName());
    return proceed;
  }
}
