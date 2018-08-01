package com.example.springframework.boot.interceptor.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 实现HandlerInterceptor接口，完成登陆拦截
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("-----" + this.getClass().getSimpleName() + " preHandle-----");
        //判断用户登录
        Object sessionUser = request.getSession().getAttribute("sessionUser");
        return null != sessionUser;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("-----" + this.getClass().getSimpleName() + " postHandle-----");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("-----" + this.getClass().getSimpleName() + " afterCompletion-----");
    }
}
