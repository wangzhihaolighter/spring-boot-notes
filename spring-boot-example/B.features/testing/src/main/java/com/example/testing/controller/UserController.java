package com.example.testing.controller;

import com.example.testing.entity.User;
import com.example.testing.repository.UserRepository;
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

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping
  public List<User> queryAll() {
    return userRepository.findAll();
  }

  @GetMapping("/{id}")
  public User queryById(@PathVariable("id") Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @PostMapping
  public Long insert(@RequestBody User user) {
    userRepository.save(user);
    return user.getId();
  }

  @PutMapping
  public void update(@RequestBody User user) {
    userRepository.save(user);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    userRepository.deleteById(id);
  }
}
