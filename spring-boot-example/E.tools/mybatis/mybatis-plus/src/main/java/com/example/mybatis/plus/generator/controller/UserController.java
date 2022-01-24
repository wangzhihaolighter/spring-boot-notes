package com.example.mybatis.plus.generator.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.plus.generator.entity.User;
import com.example.mybatis.plus.generator.service.IUserService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端控制器
 *
 * @author wangzhihao
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/user")
public class UserController {
  private final IUserService userService;

  public UserController(IUserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> queryAll() {
    return userService.list();
  }

  @GetMapping("/{id}")
  public User queryById(@PathVariable("id") Long id) {
    return userService.getById(id);
  }

  @GetMapping("/page")
  public Page<User> pageQuery(String username) {
    return userService
        .lambdaQuery()
        .like(username != null, User::getUsername, username)
        .page(Page.of(1, 5));
  }

  @PostMapping("/insert")
  public Long insert(@RequestBody User user) {
    userService.save(user);
    return user.getId();
  }

  @PutMapping("/update")
  public Boolean update(@RequestBody User user) {
    return userService.updateById(user);
  }

  @DeleteMapping("/{id}")
  public Boolean delete(@PathVariable("id") Long id) {
    return userService.removeById(id);
  }
}
