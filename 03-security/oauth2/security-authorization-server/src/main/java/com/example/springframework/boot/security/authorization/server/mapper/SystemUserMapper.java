package com.example.springframework.boot.security.authorization.server.mapper;

import com.example.springframework.boot.security.authorization.server.entity.SystemUser;

public interface SystemUserMapper {

    SystemUser getByUserName(String username);
}
