package com.example.liquibase.service;

import com.example.liquibase.dao.UserRepository;
import com.example.liquibase.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> queryAll() {
    return userRepository.findAll();
  }
}
