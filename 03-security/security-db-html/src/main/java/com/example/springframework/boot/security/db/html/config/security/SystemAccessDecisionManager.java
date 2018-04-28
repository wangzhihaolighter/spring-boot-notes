package com.example.springframework.boot.security.db.html.config.security;

import com.example.springframework.boot.security.db.html.entity.SystemPermission;
import com.example.springframework.boot.security.db.html.entity.SystemRole;
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
import java.util.List;

/**
 * 用于校验用户权限，拦截器注入
 */
@Service
public class SystemAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        //不做权限拦截路径
        if (matcherNoAuth(request)) {
            return;
        }
        //登录用户权限校验 - 管理员直接放行
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (ga instanceof SystemGrantedAuthority) {
                SystemGrantedAuthority systemGrantedAuthority = (SystemGrantedAuthority) ga;
                SystemRole systemRole = systemGrantedAuthority.getSystemRole();
                String name = systemRole.getName();
                //管理员直接放行
                //可以对其他角色进行特殊处理
                if (RoleEnum.ADMIN.toString().equals(name)) {
                    return;
                }
                List<SystemPermission> systemPermissions = systemRole.getSystemPermissions();
                for (SystemPermission systemPermission : systemPermissions) {
                    String permissionUrl = systemPermission.getUrl();
                    String permissionMethod = systemPermission.getMethod();
                    if (matchers(permissionUrl, request)) {
                        if (permissionMethod.equals(request.getMethod()) || "ALL".equals(permissionMethod)) {
                            return;
                        }
                    }
                }
            }
        }
        //无权限
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

    /**
     * 校验路径是否匹配
     */
    private boolean matchers(String url, HttpServletRequest request) {
        AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
        return matcher.matches(request);
    }

    /**
     * 校验是否为无权限路径
     */
    private boolean matcherNoAuth(HttpServletRequest request) {
        return matchers("/", request)
                //静态文件
                || matchers("/css/**", request)
                || matchers("/js/**", request)
                || matchers("/images/**", request)
                || matchers("/fonts/**", request)
                || matchers("/favicon.ico", request)
                //通用
                || matchers("/index.html", request)
                || matchers("/login", request)
                || matchers("/logout", request)
                //api
                || matchers("/api/**", request)
                ;

    }
}