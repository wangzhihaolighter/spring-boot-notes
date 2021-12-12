package com.example.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class HandlerInterceptorImpl implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.info("[{}] {} preHandle", request.getRequestURI(), this.getClass().getSimpleName());
    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {
    log.info("[{}] {} postHandle", request.getRequestURI(), this.getClass().getSimpleName());
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    log.info("[{}] {} afterCompletion", request.getRequestURI(), this.getClass().getSimpleName());
  }
}
