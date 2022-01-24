package com.example.security.config.security.userdetails;

import com.example.security.config.security.constant.SecurityConstants;
import com.example.security.entity.User;
import com.example.security.service.UserService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;

  public UserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.selectByUsername(username);
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException(String.format("账户 %s 不存在", username));
    }

    // 获取授权账户信息
    List<GrantedAuthorityImpl> authorities =
        userService.selectPermissionsByUsername(username).stream()
            .map(
                permission -> {
                  GrantedAuthorityImpl grantedAuthority = new GrantedAuthorityImpl();
                  grantedAuthority.setAuthority(
                      String.format(
                          "%s%S%S",
                          permission.getUrl(),
                          SecurityConstants.PERMISSION_METHOD_SEPARATOR,
                          permission.getMethod()));
                  return grantedAuthority;
                })
            .collect(Collectors.toList());
    return new UserDetailsImpl(null, user, authorities);
  }
}
