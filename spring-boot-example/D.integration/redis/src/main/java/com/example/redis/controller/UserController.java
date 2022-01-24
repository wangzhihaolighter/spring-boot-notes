package com.example.redis.controller;

import com.example.redis.entity.User;
import com.example.redis.service.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
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

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/query/all")
  public ResponseEntity<List<User>> queryAll() {
    return ResponseEntity.ok(userService.queryAll());
  }

  @GetMapping("/query/{id}")
  public ResponseEntity<User> query(@PathVariable("id") Long id) {
    return ResponseEntity.ok(userService.query(id));
  }

  @PostMapping("/save")
  public ResponseEntity<User> save(@RequestBody User user) {
    return ResponseEntity.ok(userService.save(user));
  }

  @PutMapping("/update")
  public ResponseEntity<User> update(@RequestBody User user) {
    return ResponseEntity.ok(userService.update(user));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    userService.delete(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/delete/all")
  public ResponseEntity<Void> deleteAll() {
    userService.deleteAll();
    return ResponseEntity.ok().build();
  }
}
