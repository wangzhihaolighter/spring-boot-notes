package com.example.hypersql.controller;

import com.example.hypersql.entity.User;
import com.example.hypersql.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<User>> queryAll() {
    return ResponseEntity.ok(userService.queryAll());
  }

  @PostMapping
  public ResponseEntity<User> save(@RequestBody User user) {
    return ResponseEntity.ok(userService.save(user));
  }

  @PutMapping
  public ResponseEntity<User> update(@RequestBody User user) {
    return ResponseEntity.ok(userService.update(user));
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteAll() {
    userService.deleteAll();
    return ResponseEntity.ok().build();
  }
}
