package com.example.springframework.boot.mybatis.mapper.web;

import com.example.springframework.boot.mybatis.mapper.entity.User;
import com.example.springframework.boot.mybatis.mapper.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

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
        //通用方法
        return userMapper.selectAll();
    }

    @GetMapping("/user/query/username")
    public List<User> getUserByUsername(@RequestParam("username") String username) {
        //类似HQL
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("username", "%" + username + "%");
        return userMapper.selectByExample(example);
    }

}
