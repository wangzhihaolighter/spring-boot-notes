package com.example.security.config.security.userdetails;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Setter
@Getter
public class GrantedAuthorityImpl implements GrantedAuthority {
  private String authority;

  @Override
  public String getAuthority() {
    return authority;
  }
}
