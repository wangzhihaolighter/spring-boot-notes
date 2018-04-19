package com.example.springframework.boot.security.swagger.config.security;

public enum RoleEnum {
    ADMIN,
    ACTUATOR,
    DEVELOPER
    ;

    @Override
    public String toString() {
        return super.toString().toUpperCase();
    }
}
