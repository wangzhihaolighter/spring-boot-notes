package com.example.mybatis.pagehelper.controller;

import com.example.mybatis.pagehelper.entity.User;
import com.example.mybatis.pagehelper.mapper.UserMapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserMapper userMapper;

  public UserController(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @GetMapping
  public List<User> queryAll() {
    return userMapper.selectAll();
  }

  @GetMapping("/page")
  public PageInfo<User> pageQuery() {
    PageMethod.startPage(1, 5);
    List<User> users = userMapper.selectAll();
    return new PageInfo<>(users);
  }
}
