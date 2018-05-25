package com.example.springframework.boot.aop.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 校验方法是否机密，机密 -> 拦截
 */
@Slf4j
@Component
public class ClassifiedInterceptor implements MethodInterceptor {

    /**
     * 切入点 - web层
     */
    @Pointcut("execution(public * com.example.springframework.boot.aop.service..*.*(..))")
    public void pointcut() {
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        log.info("[CLASSIFIED] - 校验方法是否机密");
        Classified classified = methodInvocation.getMethod().getAnnotation(Classified.class);
        if (classified != null && classified.value()) {
            //机密 -> 拦截
            throw new RuntimeException("Access Denied");
        }
        //放行
        return methodInvocation.proceed();
    }
}
