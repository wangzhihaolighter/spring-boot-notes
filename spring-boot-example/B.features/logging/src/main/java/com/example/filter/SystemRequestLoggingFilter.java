package com.example.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * 将日志所需参数填充至MDC，然后由logback打印
 */
@Slf4j
@Component
@WebFilter(urlPatterns = "/**", filterName = SystemRequestLoggingFilter.FILTER_NAME)
public class SystemRequestLoggingFilter extends AbstractRequestLoggingFilter {

    static final String FILTER_NAME = "systemRequestLoggingFilter";

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return true;
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        MDC.put("url", request.getRequestURI());

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        MDC.put("ip", ipAddress);

        //可从redis等session存储里面获取userId并填充至MDC
        MDC.put("userId", "get userId from redis");
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        MDC.remove("url");
        MDC.remove("ip");
        MDC.remove("userId");
    }
}