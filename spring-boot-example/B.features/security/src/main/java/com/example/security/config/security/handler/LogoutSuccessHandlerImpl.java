package com.example.security.config.security.handler;

import com.example.security.config.cache.CacheService;
import com.example.security.config.response.R;
import com.example.security.config.security.constant.SecurityConstants;
import com.example.security.util.JsonUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/** 登出成功处理 */
@Slf4j
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
  private final CacheService cacheService;

  public LogoutSuccessHandlerImpl(CacheService cacheService) {
    this.cacheService = cacheService;
  }

  @Override
  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    log.info("[ {} - {} ] - 登出成功", request.getMethod(), request.getRequestURI());

    String header = request.getHeader(SecurityConstants.AUTHENTICATION_HEADER_KEY);
    cacheService.removeKey(header);

    R<Void> result = R.result(String.valueOf(HttpStatus.OK.value()), "登出成功");
    response.setStatus(HttpStatus.OK.value());
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    response.getWriter().print(JsonUtils.toJsonStr(result));
  }
}
