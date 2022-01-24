package com.example.security.config.security.filter;

import com.example.security.config.cache.CacheService;
import com.example.security.config.security.constant.SecurityConstants;
import com.example.security.config.security.userdetails.UserDetailsImpl;
import com.example.security.util.JsonUtils;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** token过滤器 - 验证token有效性 */
@Slf4j
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {
  private final CacheService cacheService;

  public AuthenticationTokenFilter(CacheService cacheService) {
    this.cacheService = cacheService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    log.info("[ {} - {} ] - 验证token有效性", request.getMethod(), request.getRequestURI());

    if (Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
      chain.doFilter(request, response);
      return;
    }

    String header = request.getHeader(SecurityConstants.AUTHENTICATION_HEADER_KEY);
    if (StringUtils.isBlank(header)) {
      chain.doFilter(request, response);
      return;
    }

    String userDetailsJson = (String) cacheService.getValue(header);
    if (StringUtils.isBlank(userDetailsJson)) {
      chain.doFilter(request, response);
      return;
    }

    UserDetailsImpl userDetails = JsonUtils.parseJson(userDetailsJson, UserDetailsImpl.class);
    if (Objects.isNull(userDetails)) {
      chain.doFilter(request, response);
      return;
    }

    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(token);

    chain.doFilter(request, response);
  }
}
