package com.example.security.config.security.handler;

import com.example.security.config.response.R;
import com.example.security.util.JsonUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/** 访问受限处理 */
@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    log.info("[ {} - {} ] - 访问受限", request.getMethod(), request.getRequestURI());
    R<Void> result = R.result(String.valueOf(HttpStatus.FORBIDDEN.value()), "访问受限");
    response.setStatus(HttpStatus.OK.value());
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    response.getWriter().print(JsonUtils.toJsonStr(result));
  }
}
