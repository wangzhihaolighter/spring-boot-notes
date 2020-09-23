package com.example.springframework.boot.security.db.swagger.config.security;

import com.example.springframework.boot.security.db.swagger.entity.SystemRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * 用于封装用户角色与权限信息
 */
@Data
public class SystemGrantedAuthority implements GrantedAuthority {
    private SystemRole systemRole;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public SystemGrantedAuthority(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    public SystemRole getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    @Override
    public String getAuthority() {
        try {
            return MAPPER.writeValueAsString(systemRole);
        } catch (JsonProcessingException ignored) {
        }
        return null;
    }
}