package com.example.springframework.boot.security.database.config.security;

/**
 * 角色枚举
 */
public enum RoleEnum {
    ADMIN,
    ACTUATOR,
    DEVELOPER,
    USER
    ;

    @Override
    public String toString() {
        return super.toString().toUpperCase();
    }
}
