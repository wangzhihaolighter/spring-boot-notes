package com.example.kaptcha.util;

import java.util.UUID;

public class UuidUtil {

    /**
     * 生成captcha验证码UUID
     *
     * @return /
     */
    public static String generatorLoginTokenUuid() {
        return "login_" + UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成captcha验证码UUID
     *
     * @return /
     */
    public static String generatorCaptchaUuid() {
        return "captcha_" + UUID.randomUUID().toString().replace("-", "");
    }

}
