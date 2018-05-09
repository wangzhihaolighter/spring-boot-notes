package com.example.springframework.boot.mybatis.page.helper.web;

import com.example.springframework.boot.mybatis.page.helper.entity.User;
import com.example.springframework.boot.mybatis.page.helper.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SimpleController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String home() {
        return "hello mybatis tool -> pageHelper";
    }

    @GetMapping("/user/query/all")
    public List<User> queryUserAll() {
        return userMapper.selectAll();
    }

    @GetMapping("/user/query/page")
    public PageInfo<User> pageQueryUser(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize) {
        //mybatis分页插件
        //分页用法
        PageHelper.startPage(pageNum,pageSize);
        //排序
        PageHelper.orderBy("username");
        return new PageInfo<>(userMapper.selectAll());
    }
}
