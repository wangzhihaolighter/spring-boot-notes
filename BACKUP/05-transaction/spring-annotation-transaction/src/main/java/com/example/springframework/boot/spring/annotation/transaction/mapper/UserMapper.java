package com.example.springframework.boot.spring.annotation.transaction.mapper;

import com.example.springframework.boot.spring.annotation.transaction.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    List<User> queryAll();

    User queryById(@Param("id") Long id);

    List<User> queryByIds(List<Long> ids);

    void insert(User user);

    void batchInsert(List<User> users);

    Integer delete(@Param("id") Long id);

    Integer batchDelete(List<Long> ids);

    Integer update(User user);

    Integer batchUpdate(List<User> users);
}