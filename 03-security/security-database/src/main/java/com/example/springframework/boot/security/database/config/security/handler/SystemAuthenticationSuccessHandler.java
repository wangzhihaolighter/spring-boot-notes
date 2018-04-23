package com.example.springframework.boot.security.database.config.security.handler;

import com.example.springframework.boot.security.database.config.response.DemoResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功处理
 */
public class SystemAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        //判断是否是ajax请求
        //String ajaxHeader = request.getHeader("X-Requested-With");
        //boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        boolean isAjax = true;
        if (isAjax) {
            String principal = auth.getPrincipal().toString();
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(MAPPER.writeValueAsString(DemoResult.success(principal,"认证成功")));
            response.getWriter().flush();
        } else {
            super.onAuthenticationSuccess(request, response, auth);
        }
    }
}