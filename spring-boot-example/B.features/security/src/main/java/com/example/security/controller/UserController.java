package com.example.security.controller;

import com.example.security.config.response.R;
import com.example.security.dao.UserRepository;
import com.example.security.entity.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PreAuthorize("hasAnyAuthority('/user:GET')")
  @GetMapping
  public R<List<User>> queryAll() {
    log.info("user -  queryAll");
    return R.success(userRepository.findAll());
  }

  @PreAuthorize(
      "hasAnyAuthority(#request.requestURI + T(com.example.security.config.security.constant.SecurityConstants).PERMISSION_METHOD_SEPARATOR + #request.method)")
  @PostMapping
  public R<Long> insert(HttpServletRequest request) {
    log.info("user -  insert");
    return R.success(1L);
  }

  @PreAuthorize("@se.hasPermission()")
  @PutMapping
  public R<Integer> update() {
    log.info("user -  update");
    return R.success(1);
  }

  @PreAuthorize("@se.hasPermission()")
  @DeleteMapping
  public R<Boolean> delete() {
    log.info("user -  delete");
    return R.success(true);
  }
}
