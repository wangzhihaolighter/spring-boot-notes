package com.example.mybatis.mapper.controller;

import com.example.mybatis.mapper.entity.User;
import com.example.mybatis.mapper.mapper.UserMapper;
import io.mybatis.mapper.example.Example;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    return userMapper.selectByExample(new Example<>());
  }

  @GetMapping("/{id}")
  public User queryById(@PathVariable("id") Long id) {
    return userMapper.selectByPrimaryKey(id).orElse(null);
  }

  @GetMapping("/username")
  public List<User> queryByUsernameLike(String username) {
    return userMapper.queryByUsernameLike("%" + username + "%");
  }

  @PostMapping("/insert")
  public Long insert(@RequestBody User user) {
    userMapper.insertSelective(user);
    return user.getId();
  }

  @PutMapping("/update")
  public Integer update(@RequestBody User user) {
    return userMapper.updateByPrimaryKeySelective(user);
  }

  @DeleteMapping("/{id}")
  public Integer delete(@PathVariable("id") Long id) {
    return userMapper.deleteByPrimaryKey(id);
  }
}
