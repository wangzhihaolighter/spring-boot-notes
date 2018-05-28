package com.example.springframework.boot.aop.filter.interceptor.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

@Slf4j
public class TestWebRequestInterceptor implements WebRequestInterceptor {
    @Override
    public void preHandle(WebRequest webRequest) throws Exception {
        log.info("[ INTERCEPTOR ]-----" + this.getClass().getSimpleName() + " preHandle-----");
    }

    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) throws Exception {
        log.info("[ INTERCEPTOR ]-----" + this.getClass().getSimpleName() + " postHandle-----");
    }

    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {
        log.info("[ INTERCEPTOR ]-----" + this.getClass().getSimpleName() + " afterCompletion-----");
    }
}
