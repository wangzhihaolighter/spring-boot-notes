package com.example.security.config.security.handler;

import com.example.security.config.security.constant.SecurityConstants;
import com.example.security.config.security.userdetails.UserDetailsImpl;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 用于Spring Security表达式计算授权
 *
 * @see org.springframework.security.access.expression.SecurityExpressionRoot
 */
@Service("se")
public class SecurityExpressionRootImpl {
  private final HttpServletRequest request;

  public SecurityExpressionRootImpl(HttpServletRequest request) {
    this.request = request;
  }

  /** 通过request获取请求路径和方法，判断是否授权 */
  public boolean hasPermission() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();
    if (Objects.isNull(principal) || !(principal instanceof UserDetailsImpl)) {
      return false;
    }

    String uri = request.getRequestURI();
    String method = request.getMethod();
    String permission = uri + SecurityConstants.PERMISSION_METHOD_SEPARATOR + method;
    UserDetailsImpl userDetails = (UserDetailsImpl) principal;

    return userDetails.getAuthorities().stream()
        .anyMatch(authority -> StringUtils.equals(authority.getAuthority(), permission));
  }
}
