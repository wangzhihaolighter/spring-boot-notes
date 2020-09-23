package com.example.springframework.boot.async.transaction.mapper;

import com.example.springframework.boot.async.transaction.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    void insert(User user);

    Integer update(User user);

    Integer delete(@Param("id") Long id);

    User queryById(@Param("id") Long id);

    List<User> queryAll();

    User queryUserDetailById(@Param("id") Long id);

    List<User> queryByIds(List<Long> ids);

    void batchInsert(List<User> users);

    Integer batchUpdate(List<User> users);

    Integer batchDelete(List<Long> ids);
}