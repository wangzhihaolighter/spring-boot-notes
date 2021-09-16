package com.example.i18n.core.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
  public static HttpServletRequest getRequest() {
    ServletRequestAttributes requestAttributes = getRequestAttributes();
    if (requestAttributes == null) {
      return null;
    }
    return requestAttributes.getRequest();
  }

  public static ServletRequestAttributes getRequestAttributes() {
    RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
    return (ServletRequestAttributes) attributes;
  }
}
