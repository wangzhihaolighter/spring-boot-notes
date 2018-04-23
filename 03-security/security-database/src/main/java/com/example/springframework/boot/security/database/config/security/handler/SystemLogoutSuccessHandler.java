package com.example.springframework.boot.security.database.config.security.handler;

import com.example.springframework.boot.security.database.config.response.DemoResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义security登出成功
 */
public class SystemLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //判断是否是ajax请求
        //String ajaxHeader = request.getHeader("X-Requested-With");
        //boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        boolean isAjax = true;
        if (isAjax) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(MAPPER.writeValueAsString(DemoResult.success("认证登出成功")));
            response.getWriter().flush();
        } else {
            super.onLogoutSuccess(request, response, authentication);
        }
    }
}
