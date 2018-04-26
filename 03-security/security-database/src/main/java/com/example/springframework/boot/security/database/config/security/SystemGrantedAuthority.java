package com.example.springframework.boot.security.database.config.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class SystemGrantedAuthority implements GrantedAuthority {
    private String permissionMethod;
    private String permissionUrl;

    public SystemGrantedAuthority(String permissionMethod, String permissionUrl) {
        this.permissionMethod = permissionMethod;
        this.permissionUrl = permissionUrl;
    }

    @Override
    public String getAuthority() {
        return this.permissionMethod + "&" + this.permissionUrl;
    }
}