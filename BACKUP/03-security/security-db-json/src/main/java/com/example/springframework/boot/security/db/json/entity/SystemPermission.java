package com.example.springframework.boot.security.db.json.entity;

import lombok.Data;

@Data
public class SystemPermission {
    private Long id;
    private Long pid;
    private String name;
    private String description;
    private String url;
    private String method;
    private Boolean flag;
}
