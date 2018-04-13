package com.example.springframework.boot.security.database.config.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class SystemGrantedAuthority implements GrantedAuthority {
    private String permissionName;
    private String permissionUrl;

    public SystemGrantedAuthority(String permissionName, String permissionUrl) {
        this.permissionName = permissionName;
        this.permissionUrl = permissionUrl;
    }

    @Override
    public String getAuthority() {
        return this.permissionName + "&" + this.permissionUrl;
    }
}