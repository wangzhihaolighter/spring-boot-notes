package com.example.springframework.boot.mybatis.mapper.generator.web;

import com.example.springframework.boot.mybatis.mapper.generator.entity.User;
import com.example.springframework.boot.mybatis.mapper.generator.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SimpleController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String home() {
        return "hello mybatis mapper plugin generator";
    }

    @GetMapping("/user/query/all")
    public List<User> getUserAll() {
        return userMapper.selectAll();
    }

}
