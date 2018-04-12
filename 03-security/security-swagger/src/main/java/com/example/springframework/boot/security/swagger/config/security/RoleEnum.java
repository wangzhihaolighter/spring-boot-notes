package com.example.springframework.boot.security.swagger.config.security;

public enum RoleEnum {
    ACTUATOR,
    DEVELOPER
    ;

    @Override
    public String toString() {
        return super.toString().toUpperCase();
    }
}
