package com.example.caffeine.controller;

import com.example.caffeine.entity.User;
import com.example.caffeine.service.UserService;
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

  @GetMapping
  public ResponseEntity<List<User>> queryAll() {
    return ResponseEntity.ok(userService.queryAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> query(@PathVariable("id") Long id) {
    return ResponseEntity.ok(userService.query(id));
  }

  @PostMapping
  public ResponseEntity<User> save(@RequestBody User user) {
    return ResponseEntity.ok(userService.save(user));
  }

  @PutMapping
  public ResponseEntity<User> update(@RequestBody User user) {
    return ResponseEntity.ok(userService.update(user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    userService.delete(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteAll() {
    userService.deleteAll();
    return ResponseEntity.ok().build();
  }
}
