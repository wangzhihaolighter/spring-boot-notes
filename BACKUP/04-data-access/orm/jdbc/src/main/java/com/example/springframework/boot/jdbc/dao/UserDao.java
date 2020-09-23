package com.example.springframework.boot.jdbc.dao;

import com.example.springframework.boot.jdbc.entity.User;

import java.util.List;

public interface UserDao {

    Long insert(User user);

    Integer update(User user);

    Integer delete(Long id);

    Integer batchDelete(List<Long> ids);

    User selectById(Long id);

    List<User> selectAll();

}
