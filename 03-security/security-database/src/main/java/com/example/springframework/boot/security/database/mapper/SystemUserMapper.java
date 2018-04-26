package com.example.springframework.boot.security.database.mapper;

import com.example.springframework.boot.security.database.entity.SystemPermission;
import com.example.springframework.boot.security.database.entity.SystemRole;
import com.example.springframework.boot.security.database.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemUserMapper {
    SystemUser getByUserName(String userName);

    SystemUser getById(Long id);

    SystemRole getRoleByUserId(Long id);

    List<SystemPermission> getPermissonsByUserId(Long id);
}
