package com.example.springframework.boot.security.database.mapper;

import com.example.springframework.boot.security.database.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemUserMapper {
    SystemUser getByUserName(String userName);
}
