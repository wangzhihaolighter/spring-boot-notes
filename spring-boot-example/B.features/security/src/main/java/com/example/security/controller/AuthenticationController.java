package com.example.security.controller;

import com.example.security.config.dto.LoginRequestDTO;
import com.example.security.config.response.R;
import com.example.security.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/login")
  public R<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
    log.info("login");
    return R.success(authenticationService.login(loginRequestDTO));
  }
}
