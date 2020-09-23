package com.example.springframework.boot.security.db.html.mapper;

import com.example.springframework.boot.security.db.html.entity.SystemUser;

public interface SystemUserMapper {

    SystemUser getByUserName(String username);
}
