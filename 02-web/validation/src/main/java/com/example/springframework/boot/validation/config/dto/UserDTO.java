package com.example.springframework.boot.validation.config.dto;

import com.example.springframework.boot.validation.config.annotation.CannotHaveProhibitedWord;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserDTO {
    /*
    分组校验:同一个类，在不同的使用场景下有不同的校验规则
    */

    @NotBlank(message = "用户名不得为空",groups = {Adult.class,Minor.class})
    private String username;
    @NotBlank(message = "用户密码不得为空",groups = {Adult.class,Minor.class})
    private String password;
    @Size(max = 50,message = "描述最多50个字符",groups = {Adult.class,Minor.class})
    private String description;
    @Min(value = 16, message = "年龄不得小于16", groups = {Adult.class})
    @Max(value = 16, message = "年龄不得大于16", groups = {Minor.class})
    private Integer age;
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误",groups = {Adult.class,Minor.class})
    @NotBlank(message = "手机号码不能为空", groups = {Adult.class})
    private String phone;
    @Email(message = "邮箱格式错误",groups = {Adult.class,Minor.class})
    private String email;

    /*
    自定义校验：
     */

    @CannotHaveProhibitedWord(message = "留言中包含违禁词",groups = {Adult.class,Minor.class})
    private String message;

    /**
     * 成年人分组
     */
    public interface Adult {
    }

    /**
     * 未成年人分组
     */
    public interface Minor {
    }
}
