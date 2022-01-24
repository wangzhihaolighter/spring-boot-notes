package com.example.core.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet工具类
 *
 * @author wangzhihao
 */
public class ServletUtils {
  public static HttpServletRequest getRequest() {
    return getRequestAttributes().getRequest();
  }

  public static ServletRequestAttributes getRequestAttributes() {
    RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
    return (ServletRequestAttributes) attributes;
  }
}
