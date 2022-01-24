package com.example.transaction.annotation.controller;

import com.example.transaction.annotation.config.exception.InsertException;
import com.example.transaction.annotation.config.exception.OtherException;
import com.example.transaction.annotation.entity.User;
import com.example.transaction.annotation.service.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> query() {
    return userService.query();
  }

  @GetMapping("/readOnly")
  public void queryReadOnly() {
    userService.queryReadOnlyTest();
  }

  @PostMapping
  public void save() throws OtherException, InsertException {
    userService.saveTest();
  }

  @PutMapping
  public void update() {
    userService.updateTest();
  }

  @DeleteMapping
  public void delete() {
    userService.deleteTest();
  }
}
