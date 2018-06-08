package com.example.springframework.boot.async.transaction.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private UserInfo userInfo;
    private List<UserRole> userRoles;
}