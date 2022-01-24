package com.example.security.service.impl;

import com.example.security.config.cache.CacheService;
import com.example.security.config.dto.LoginRequestDTO;
import com.example.security.config.security.userdetails.UserDetailsImpl;
import com.example.security.service.AuthenticationService;
import com.example.security.util.JsonUtils;
import java.util.UUID;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
  private final AuthenticationManager authenticationManager;
  private final CacheService cacheService;

  public AuthenticationServiceImpl(
      AuthenticationManager authenticationManager, CacheService cacheService) {
    this.authenticationManager = authenticationManager;
    this.cacheService = cacheService;
  }

  @Override
  public String login(LoginRequestDTO loginRequestDTO) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    String token = UUID.randomUUID().toString();
    userDetails.setToken(token);
    cacheService.putValue(token, JsonUtils.toJsonStr(userDetails));
    return token;
  }
}
