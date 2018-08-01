package com.example.springframework.boot.transaction.jta.user.feign.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class People implements Serializable {
    private Long id;
    private String name;
    private String password;
}