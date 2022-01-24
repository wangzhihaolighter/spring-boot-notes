package com.example.kaptcha.util;

import java.util.UUID;

public class IdUtil {

  /**
   * 生成登录token凭证
   *
   * @return /
   */
  public static String generatorLoginTokenUuid() {
    return "login_" + UUID.randomUUID().toString().replace("-", "");
  }

  /**
   * 生成验证码UUID
   *
   * @return /
   */
  public static String generatorCaptchaUuid() {
    return "captcha_" + UUID.randomUUID().toString().replace("-", "");
  }
}
