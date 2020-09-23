package com.example.springframework.boot.security.db.json.config.security;

/**
 * 角色枚举
 */
public enum RoleEnum {
    //匿名用户
    ANONYMOUSUSER
    //登录用户角色
    , ADMIN, ACTUATOR, DEVELOPER, USER;

    @Override
    public String toString() {
        return super.toString().toUpperCase();
    }
}
