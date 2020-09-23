package com.example.springframework.boot.security.db.config.security;

import org.springframework.security.access.ConfigAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于获取请求参数
 */
public class SystemConfigAttribute implements ConfigAttribute {

    private final HttpServletRequest httpServletRequest;

    public SystemConfigAttribute(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public String getAttribute() {
        return null;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }
}