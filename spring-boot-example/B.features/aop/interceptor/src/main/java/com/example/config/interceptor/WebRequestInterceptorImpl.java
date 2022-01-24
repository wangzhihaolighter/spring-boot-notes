package com.example.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

@Slf4j
public class WebRequestInterceptorImpl implements WebRequestInterceptor {
  @Override
  public void preHandle(WebRequest request) throws Exception {
    log.info(
        "[{}] {} preHandle",
        ((DispatcherServletWebRequest) request).getRequest().getRequestURI(),
        this.getClass().getSimpleName());
  }

  @Override
  public void postHandle(WebRequest request, ModelMap modelMap) throws Exception {
    log.info(
        "[{}] {} postHandle",
        ((DispatcherServletWebRequest) request).getRequest().getRequestURI(),
        this.getClass().getSimpleName());
  }

  @Override
  public void afterCompletion(WebRequest request, Exception e) throws Exception {
    log.info(
        "[{}] {} afterCompletion",
        ((DispatcherServletWebRequest) request).getRequest().getRequestURI(),
        this.getClass().getSimpleName());
  }
}
