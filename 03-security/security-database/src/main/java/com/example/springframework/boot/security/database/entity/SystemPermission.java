package com.example.springframework.boot.security.database.entity;

import lombok.Data;

@Data
public class SystemPermission {
    private Long id;
    private Long pid;
    private String name;
    private String description;
    private String url;
    private Boolean flag;
}
