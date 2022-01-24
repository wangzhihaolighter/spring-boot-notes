package com.example.springfox.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户DTO")
public class UserDTO {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "用户名", required = true, example = "飞翔的小白菜")
    private String username;
    @ApiModelProperty(value = "密码", required = true, example = "小白菜")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
