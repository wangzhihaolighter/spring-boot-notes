package com.example.springframework.boot.aop.filter.interceptor.config.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectConfig {

    @Bean
    public TestAspect testAspect() {
        return new TestAspect();
    }

}
