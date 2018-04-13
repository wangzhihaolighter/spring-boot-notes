package com.example.springframework.boot.security.database.config.security;

import com.example.springframework.boot.security.database.entity.SystemPermission;
import com.example.springframework.boot.security.database.entity.SystemUser;
import com.example.springframework.boot.security.database.mapper.SystemPermissionMapper;
import com.example.springframework.boot.security.database.mapper.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户的校验、权限路径获取
 */
@Component
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private SystemPermissionMapper systemPermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser user = systemUserMapper.getByUserName(username);
        if (user != null) {
            List<SystemPermission> systemPermissions = systemPermissionMapper.getByUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (SystemPermission systemPermission : systemPermissions) {
                if (systemPermission != null && systemPermission.getName() != null) {
                    GrantedAuthority grantedAuthority = new SystemGrantedAuthority(systemPermission.getName(), systemPermission.getUrl());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            user.setAuthorities(grantedAuthorities);
            return user;
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }
}
