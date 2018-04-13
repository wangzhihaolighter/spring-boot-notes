package com.example.springframework.boot.security.database.entity;

import lombok.Data;

@Data
public class SystemRole {
    private Long id;
    private String name;
    private Integer roleLevel;
    private String description;
    private String menuItems;
    private Boolean flag;
}
