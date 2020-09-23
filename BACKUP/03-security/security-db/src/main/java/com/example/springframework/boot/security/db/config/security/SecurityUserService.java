package com.example.springframework.boot.security.db.config.security;

import com.example.springframework.boot.security.db.entity.SystemRole;
import com.example.springframework.boot.security.db.entity.SystemUser;
import com.example.springframework.boot.security.db.mapper.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义用户的校验、角色、权限路径获取
 */
@Component
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser user = systemUserMapper.getByUserName(username);
        if (null == user) {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
        List<SystemRole> systemRoles = user.getSystemRoles();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (SystemRole systemRole : systemRoles) {
            if (null == systemRole || null == systemRole.getSystemPermissions() || systemRole.getSystemPermissions().size() == 0) {
                continue;
            }
            GrantedAuthority grantedAuthority = new SystemGrantedAuthority(systemRole);
            grantedAuthorities.add(grantedAuthority);
        }
        user.setAuthorities(grantedAuthorities);
        return user;
    }
}
