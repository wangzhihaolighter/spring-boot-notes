package com.example.config.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

@Slf4j
@Order(2)
@WebFilter(urlPatterns = "/*", filterName = Test2Filter.FILTER_NAME)
public class Test2Filter implements Filter {

  static final String FILTER_NAME = "Test2Filter";

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("[{}] init", FILTER_NAME);
  }

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    log.info("[{}] doFilter start : before servlet call service", FILTER_NAME);
    filterChain.doFilter(servletRequest, servletResponse);
    log.info("[{}] doFilter end : after servlet call service", FILTER_NAME);
  }

  @Override
  public void destroy() {
    log.info("[{}] destroy", FILTER_NAME);
  }
}
