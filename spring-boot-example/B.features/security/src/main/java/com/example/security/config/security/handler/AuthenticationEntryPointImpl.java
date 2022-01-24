package com.example.security.config.security.handler;

import com.example.security.config.response.R;
import com.example.security.util.JsonUtils;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/** 身份验证失败处理 */
@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
      throws IOException {
    log.info("[ {} - {} ] - 身份验证失败", request.getMethod(), request.getRequestURI());
    R<Void> result = R.result(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "身份验证失败");
    response.setStatus(HttpStatus.OK.value());
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    response.getWriter().print(JsonUtils.toJsonStr(result));
  }
}
