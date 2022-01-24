package com.example.mapstruct.controller;

import com.example.mapstruct.config.dto.OtherDTO;
import com.example.mapstruct.config.dto.UserDTO;
import com.example.mapstruct.config.mapstruct.UserMapper;
import com.example.mapstruct.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
  private final UserMapper userMapper;

  public SimpleController(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @GetMapping("/")
  public UserDTO map() {
    User user = new User();
    user.setId(1L);
    user.setUsername("mapstruct");
    user.setPassword("123456");
    user.setTelephone("13800000000");
    return userMapper.map(user);
  }

  @GetMapping("/several")
  public UserDTO severalSourceMap() {
    User user = new User();
    user.setId(1L);
    user.setUsername("several source mapstruct");
    user.setPassword("123456");
    user.setTelephone("13800000000");
    OtherDTO otherDTO = new OtherDTO();
    otherDTO.setEmail("123@qq.com");
    return userMapper.map(user, otherDTO);
  }
}
