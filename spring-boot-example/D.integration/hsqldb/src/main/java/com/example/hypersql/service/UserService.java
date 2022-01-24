package com.example.hypersql.service;

import com.example.hypersql.dao.UserRepository;
import com.example.hypersql.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> queryAll() {
    return userRepository.findAll();
  }

  public User save(User user) {
    user.setId(null);
    return userRepository.save(user);
  }

  public User update(User user) {
    return userRepository.save(user);
  }

  public void deleteAll() {
    userRepository.deleteAll();
  }
}
