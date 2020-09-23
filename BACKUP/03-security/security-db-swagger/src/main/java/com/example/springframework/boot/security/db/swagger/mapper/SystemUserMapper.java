package com.example.springframework.boot.security.db.swagger.mapper;

import com.example.springframework.boot.security.db.swagger.entity.SystemUser;

public interface SystemUserMapper {

    SystemUser getByUserName(String username);
}
