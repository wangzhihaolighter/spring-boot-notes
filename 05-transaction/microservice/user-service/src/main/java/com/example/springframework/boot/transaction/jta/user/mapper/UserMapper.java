package com.example.springframework.boot.transaction.jta.user.mapper;

import com.example.springframework.boot.transaction.jta.user.entity.User;

import java.util.List;

public interface UserMapper {
    List<User> queryAll();

    void insert(User user);

    void clean();
}