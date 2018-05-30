package com.example.springframework.boot.aop.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 校验方法是否机密，机密 -> 拦截
 */
@Slf4j
public class ClassifiedInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        log.info("[CLASSIFIED] - 校验方法是否机密");
        Classified classified = methodInvocation.getMethod().getAnnotation(Classified.class);
        if (classified != null && classified.value()) {
            //机密 -> 拦截
            throw new RuntimeException("[CLASSIFIED] - Access Denied");
        }
        //放行
        return methodInvocation.proceed();
    }

}
