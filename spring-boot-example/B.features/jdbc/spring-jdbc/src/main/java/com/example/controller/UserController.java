package com.example.controller;

import com.example.dao.UserDao;
import com.example.domain.User;
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

  private final UserDao userDao;

  public UserController(UserDao userDao) {
    this.userDao = userDao;
  }

  @GetMapping
  public List<User> queryAll() {
    return userDao.selectAll();
  }

  @GetMapping("/{id}")
  public User queryById(@PathVariable("id") Long id) {
    return userDao.selectById(id);
  }

  @PostMapping
  public Long insert(@RequestBody User user) {
    return userDao.insert(user);
  }

  @PutMapping
  public Integer update(@RequestBody User user) {
    return userDao.update(user);
  }

  @DeleteMapping("/{id}")
  public Integer delete(@PathVariable("id") Long id) {
    return userDao.delete(id);
  }
}
