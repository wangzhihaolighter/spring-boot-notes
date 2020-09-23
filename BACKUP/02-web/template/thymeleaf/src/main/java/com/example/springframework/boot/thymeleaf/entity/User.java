package com.example.springframework.boot.thymeleaf.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
}
