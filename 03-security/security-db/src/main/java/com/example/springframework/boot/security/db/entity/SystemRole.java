package com.example.springframework.boot.security.db.entity;

import lombok.Data;

import java.util.List;

@Data
public class SystemRole {
    private Long id;
    private String name;
    private Integer roleLevel;
    private String description;
    private String menuItems;
    private Boolean flag;
    private List<SystemPermission> systemPermissions;
}
