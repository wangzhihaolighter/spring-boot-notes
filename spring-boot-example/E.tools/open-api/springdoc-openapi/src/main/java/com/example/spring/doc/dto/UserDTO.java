package com.example.spring.doc.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "用户DTO")
public class UserDTO {
    @Schema(description = "id")
    private Long id;
    @Schema(description = "用户名", required = true, example = "飞翔的小白菜")
    private String username;
    @Schema(description = "密码", required = true, example = "小白菜")
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
