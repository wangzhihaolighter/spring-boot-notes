package com.example.springframework.boot.security.db.json.config.security.handler;

import com.example.springframework.boot.security.db.json.config.response.DemoResult;
import com.example.springframework.boot.security.db.json.config.response.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义security登录错误处理
 */
public class SystemAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //判断是否是ajax请求
        //String ajaxHeader = request.getHeader("X-Requested-With");
        //boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        boolean isAjax = true;
        if (isAjax) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(MAPPER.writeValueAsString(DemoResult.fail(ResultCodeEnum.AUTH_ERROR)));
            response.getWriter().flush();
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
