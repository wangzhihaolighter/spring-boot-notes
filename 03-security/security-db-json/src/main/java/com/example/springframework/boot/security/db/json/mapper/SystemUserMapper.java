package com.example.springframework.boot.security.db.json.mapper;

import com.example.springframework.boot.security.db.json.entity.SystemUser;

public interface SystemUserMapper {

    SystemUser getByUserName(String username);
}
