package com.example.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Component
@WebFilter(urlPatterns = "/**", filterName = WebLogFilter.FILTER_NAME)
public class WebLogFilter implements Filter {

    static final String FILTER_NAME = "webLogFilter";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(FILTER_NAME + " -> init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info(FILTER_NAME + " -> doFilter start : before servlet call service");

        //记录用户访问行为
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        long startTime = System.currentTimeMillis();
        log.info("[WEB LOG] URL : " + request.getRequestURL().toString());
        log.info("[WEB LOG] HTTP_METHOD : " + request.getMethod());
        log.info("[WEB LOG] IP : " + request.getRemoteAddr());
        StringBuilder args = new StringBuilder();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            args.append(entry.getKey()).append(":").append(Arrays.toString(entry.getValue())).append(";");
        }
        log.info("[WEB LOG] ARGS : " + args);

        //doFilter...
        filterChain.doFilter(servletRequest, servletResponse);

        long endTime = System.currentTimeMillis();
        log.info("[WEB LOG] SPEND TIME (ms) : " + (endTime - startTime));

        log.info(FILTER_NAME + " -> doFilter end : after servlet call service");
    }

    @Override
    public void destroy() {
        log.info(FILTER_NAME + " -> destroy : system exit");
    }
}