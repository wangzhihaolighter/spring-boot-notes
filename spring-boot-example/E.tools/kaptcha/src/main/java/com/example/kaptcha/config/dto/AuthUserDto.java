package com.example.kaptcha.config.dto;

import lombok.Data;

@Data
public class AuthUserDto {
    private String username;
    private String password;
    private String captchaUuid;
    private String captchaText;
}
