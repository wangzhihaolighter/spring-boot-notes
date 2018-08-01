package com.example.springframework.boot.aop.filter.interceptor.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Slf4j
@Order(4)
@Component
@WebFilter(urlPatterns = "/**", filterName = TestFilter.FILTER_NAME)
public class TestFilter implements Filter {

    static final String FILTER_NAME = "[ FILTER ]testFilter";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(FILTER_NAME + " -> init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info(FILTER_NAME + " -> doFilter start : before servlet call service");

        //doFilter...
        filterChain.doFilter(servletRequest, servletResponse);

        log.info(FILTER_NAME + " -> doFilter end : after servlet call service");
    }

    @Override
    public void destroy() {
        log.info(FILTER_NAME + " -> destroy : system exit");
    }
}