package com.example.controller;

import com.example.config.dto.UserDTO;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

  @GetMapping
  public List<UserDTO> getUserAll() {
    return null;
  }

  @GetMapping("/{id}")
  public UserDTO getUserById(@PathVariable("id") Long id) {
    return null;
  }

  @PostMapping
  public Long saveUser(@Validated @RequestBody UserDTO user) {
    System.out.println(user);
    return 1L;
  }

  @DeleteMapping
  public void deleteUser(@NotNull @RequestParam("id") Long id) {}
}
