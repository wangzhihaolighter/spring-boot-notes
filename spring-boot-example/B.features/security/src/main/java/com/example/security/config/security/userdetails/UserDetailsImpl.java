package com.example.security.config.security.userdetails;

import com.example.security.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

  /** 凭证 */
  private String token;

  /** 用户信息 */
  private User user;

  /** 授权 */
  private Collection<GrantedAuthorityImpl> authorities = Collections.emptyList();

  @Override
  public Collection<GrantedAuthorityImpl> getAuthorities() {
    return authorities;
  }

  @JsonIgnore
  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return user.getPassword();
  }

  /** 是否可用 */
  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }

  /** 账户是否未过期 */
  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /** 账户是否未锁定 */
  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /** 账户凭据(密码)是否未过期 */
  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
}
