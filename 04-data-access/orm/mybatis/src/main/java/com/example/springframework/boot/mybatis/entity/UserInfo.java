package com.example.springframework.boot.mybatis.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserInfo implements Serializable {
    private Long id;
    private Long userId;
    private String country;
    private String province;
}