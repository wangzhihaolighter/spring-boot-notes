package com.example.springframework.boot.security.db.swagger.config.security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用于注入自定义的权限拦截器，需要实现
 */
@Service
public class SystemInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    /**
     * 此方法是为了判定用户请求的url 是否在security的权限表中
     * 如果在权限表中，则返回给decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        ConfigAttribute configAttribute = new SystemConfigAttribute(request);
        allAttributes.add(configAttribute);
        return allAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}