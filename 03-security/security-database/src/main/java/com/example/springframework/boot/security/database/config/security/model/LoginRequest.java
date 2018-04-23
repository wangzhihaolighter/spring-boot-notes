package com.example.springframework.boot.security.database.config.security.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
