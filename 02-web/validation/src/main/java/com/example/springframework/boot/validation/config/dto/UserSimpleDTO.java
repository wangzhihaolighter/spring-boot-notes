package com.example.springframework.boot.validation.config.dto;

import com.example.springframework.boot.validation.config.annotation.CannotHaveProhibitedWord;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserSimpleDTO {
    @NotBlank(message = "用户名不得为空")
    @Size(max = 10,message = "用户名不得超过10个字符")
    private String username;
    @NotBlank(message = "密码不得为空")
    @Size(max = 16,message = "用户密码不得超过16个字符")
    private String password;
    @CannotHaveProhibitedWord(message = "留言包含违禁词")
    private String message;
}
