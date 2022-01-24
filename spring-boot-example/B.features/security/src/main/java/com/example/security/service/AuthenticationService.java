package com.example.security.service;

import com.example.security.config.dto.LoginRequestDTO;

public interface AuthenticationService {

  /**
   * 登录
   *
   * @param loginRequestDTO /
   * @return 用户凭证
   */
  String login(LoginRequestDTO loginRequestDTO);
}
