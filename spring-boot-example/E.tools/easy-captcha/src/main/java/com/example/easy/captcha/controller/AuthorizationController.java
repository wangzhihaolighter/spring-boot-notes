package com.example.easy.captcha.controller;

import com.example.easy.captcha.config.dto.AuthUserDto;
import com.example.easy.captcha.config.dto.CaptchaDto;
import com.wf.captcha.SpecCaptcha;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

  private static final Map<String, String> CAPTCHA_CACHE_MAP = new ConcurrentHashMap<>();

  @GetMapping("/captcha")
  public ResponseEntity<CaptchaDto> getCaptcha() {
    // 生成验证码
    SpecCaptcha captcha = new SpecCaptcha(110, 36);
    captcha.setLen(4);
    String text = captcha.text();
    String uuid = UUID.randomUUID().toString().replace("-", "");

    // 验证码信息存入缓存
    CAPTCHA_CACHE_MAP.put(uuid, text);

    // 响应验证码信息
    CaptchaDto captchaDto = new CaptchaDto();
    captchaDto.setUuid(uuid);
    captchaDto.setImgBase64(captcha.toBase64());
    return ResponseEntity.ok(captchaDto);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody AuthUserDto authUserDto) {
    String value = CAPTCHA_CACHE_MAP.get(authUserDto.getCaptchaUuid());
    if (value == null || !value.equalsIgnoreCase(authUserDto.getCaptchaText())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("验证码错误");
    }

    return ResponseEntity.ok("access-token-value");
  }
}
