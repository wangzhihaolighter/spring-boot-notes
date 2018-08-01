package com.example.springframework.boot.async.transaction.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRole implements Serializable {
    private Long id;
    private Long userId;
    private String role;
}