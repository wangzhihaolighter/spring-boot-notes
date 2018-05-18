package com.example.springframework.boot.mongo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String id;
    private String username;
    private String password;
}