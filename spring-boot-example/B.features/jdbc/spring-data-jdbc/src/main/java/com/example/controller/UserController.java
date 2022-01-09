package com.example.controller;

import com.example.dao.UserRepository;
import com.example.domain.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping
  public Iterable<User> queryAll() {
    return userRepository.findAll();
  }

  @GetMapping("/{id}")
  public User queryById(@PathVariable("id") Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @GetMapping("/and")
  public User findUsersByUsernameAndPassword(String username, String password) {
    return userRepository.findUsersByUsernameAndPassword(username, password);
  }

  @GetMapping("/like")
  public List<User> findUsersByUsernameLike(String username) {
    return userRepository.findUsersByUsernameLike("%" + username + "%");
  }

  @GetMapping("/order")
  public List<User> findUsersByIdNotNullOrderByIdDesc() {
    return userRepository.findUsersByIdNotNullOrderByIdDesc();
  }

  @GetMapping("/count")
  public Integer countUsers() {
    return userRepository.countUsersByIdNotNull();
  }

  @GetMapping("/page")
  public Page<User> pageQueryUsersByUsernameLike(
      String username, Integer pageNum, Integer pageSize) {
    PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
    return userRepository.queryByUsernameLike(pageRequest, "%" + username + "%");
  }

  @GetMapping("/sort")
  public List<User> sortQueryUsersByUsernameLike(String username) {
    Sort sort = Sort.by(Sort.Order.desc("id"));
    return userRepository.queryByUsernameLike(sort, "%" + username + "%");
  }

  @GetMapping("/query")
  public List<User> selectByUsername(@RequestParam("username") String username) {
    return userRepository.selectByUsername(username);
  }

  @PostMapping
  public Long insert(@RequestBody User user) {
    userRepository.save(user);
    return user.getId();
  }

  @PutMapping
  public User update(@RequestBody User user) {
    // save方法在有主键的情况下，会进行更新
    return userRepository.save(user);
  }

  @PutMapping("/native")
  public Integer updateUser(@RequestBody User user) {
    return userRepository.updateUser(user.getId(), user.getUsername(), user.getPassword());
  }

  @DeleteMapping("/{id}")
  public Boolean delete(@PathVariable("id") Long id) {
    userRepository.deleteById(id);
    return true;
  }
}
