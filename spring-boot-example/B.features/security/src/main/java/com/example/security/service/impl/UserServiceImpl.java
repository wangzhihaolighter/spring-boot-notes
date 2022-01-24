package com.example.security.service.impl;

import com.example.security.dao.UserRepository;
import com.example.security.entity.Permission;
import com.example.security.entity.User;
import com.example.security.service.UserService;
import java.util.List;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User selectByUsername(String username) {
    User user = new User();
    user.setUsername(username);
    return userRepository.findOne(Example.of(user)).orElse(null);
  }

  @Override
  public List<Permission> selectPermissionsByUsername(String username) {
    return userRepository.selectPermissionsByUsername(username);
  }
}
