package com.example.springframework.boot.security.database.mapper;

import com.example.springframework.boot.security.database.entity.SystemPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemPermissionMapper {
    List<SystemPermission> getByUserId(Long id);

    SystemPermission getById(Long id);
}
