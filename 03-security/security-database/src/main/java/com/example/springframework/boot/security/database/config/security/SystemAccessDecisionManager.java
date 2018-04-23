package com.example.springframework.boot.security.database.config.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Service
public class SystemAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String url, method;
        //校验是否直接放行：1.管理员 2.静态文件/通用接口/api接口
        if (RoleEnum.ADMIN.toString().equals(authentication.getPrincipal())
                //静态文件
                || matchers("/css/**", request)
                || matchers("/js/**", request)
                || matchers("/images/**", request)
                || matchers("/fonts/**", request)
                || matchers("/favicon.ico", request)
                //通用接口
                || matchers("/", request)
                || matchers("/welcome", request)
                //api接口
                || matchers("/rest/**", request)
                ) {
            return;
        } else {
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (ga instanceof SystemGrantedAuthority) {
                    SystemGrantedAuthority systemGrantedAuthority = (SystemGrantedAuthority) ga;
                    url = systemGrantedAuthority.getPermissionUrl();
                    method = systemGrantedAuthority.getPermissionName();
                    if (matchers(url, request)) {
                        if (method.equals(request.getMethod()) || "ALL".equals(method)) {
                            return;
                        }
                    }
                }
            }
        }
        throw new AccessDeniedException("no right");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    private boolean matchers(String url, HttpServletRequest request) {
        AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
        return matcher.matches(request);
    }
}