package com.example.springframework.boot.security.database.mapper;

import com.example.springframework.boot.security.database.entity.SystemPermission;
import com.example.springframework.boot.security.database.entity.SystemRole;

import java.util.List;

public interface SystemRoleMapper {
    SystemRole getById(Long id);

    List<SystemPermission> getPermissonsByRoleId(Long id);
}
