package com.example.easy.captcha.config.dto;

import lombok.Data;

@Data
public class CaptchaDto {
    private String uuid;
    private String imgBase64;
}
