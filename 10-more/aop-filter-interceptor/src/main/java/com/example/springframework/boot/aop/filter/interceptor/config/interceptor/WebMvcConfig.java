package com.example.springframework.boot.aop.filter.interceptor.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public TestHandlerInterceptor testHandlerInterceptor() {
        return new TestHandlerInterceptor();
    }

    @Bean
    public TestWebRequestInterceptor testWebRequestInterceptor() {
        return new TestWebRequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testHandlerInterceptor()).addPathPatterns("/**").order(1);
        registry.addWebRequestInterceptor(testWebRequestInterceptor()).addPathPatterns("/**").order(1);
    }
}