package com.example.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

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

        long startTime = System.currentTimeMillis();

        //doFilter...
        filterChain.doFilter(servletRequest, servletResponse);

        long endTime = System.currentTimeMillis();

        log.info("[WEB LOG] SPEND TIME : {} ms", (endTime - startTime));

        log.info(FILTER_NAME + " -> doFilter end : after servlet call service");
    }

    @Override
    public void destroy() {
        log.info(FILTER_NAME + " -> destroy : system exit");
    }
}