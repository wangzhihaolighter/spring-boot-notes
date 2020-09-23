package com.example.springframework.boot.people.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class People implements Serializable {
    private Long id;
    private String name;
    private String password;
}