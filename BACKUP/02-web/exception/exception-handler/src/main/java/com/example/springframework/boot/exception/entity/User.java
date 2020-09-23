package com.example.springframework.boot.exception.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
}
