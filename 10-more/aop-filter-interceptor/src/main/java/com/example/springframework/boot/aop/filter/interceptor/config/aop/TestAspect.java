package com.example.springframework.boot.aop.filter.interceptor.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * 日志切面，记录web层访问日志
 */
@Slf4j
@Order(1)
@Aspect
public class TestAspect {

    @Pointcut("execution(public * com.example.springframework.boot.aop.filter.interceptor.web..*.*(..))")
    public void test() {
    }

    @Around("test()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        log.info("[aspect] do around start");
        Object proceed = point.proceed();
        log.info("[aspect] do around end");
        return proceed;
    }

}
