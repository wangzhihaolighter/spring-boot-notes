package com.example.springframework.boot.security.db.mapper;

import com.example.springframework.boot.security.db.entity.SystemUser;

public interface SystemUserMapper {

    SystemUser getByUserName(String username);
}
