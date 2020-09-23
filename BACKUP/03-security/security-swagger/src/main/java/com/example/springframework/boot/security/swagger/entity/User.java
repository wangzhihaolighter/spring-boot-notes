package com.example.springframework.boot.security.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@ApiModel("用户")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "用户名", required = true, example = "飞翔的小白菜")
    private String username;
    @ApiModelProperty(value = "密码", required = true, example = "小白菜")
    private String password;
}
