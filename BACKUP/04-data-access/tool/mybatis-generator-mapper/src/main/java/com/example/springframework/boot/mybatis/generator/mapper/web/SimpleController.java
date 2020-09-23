package com.example.springframework.boot.mybatis.generator.mapper.web;

import com.example.springframework.boot.mybatis.generator.mapper.entity.User;
import com.example.springframework.boot.mybatis.generator.mapper.mapper.UserMapper;
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
    public List<User> queryUserAll(){
        return userMapper.selectAll();
    }

}
