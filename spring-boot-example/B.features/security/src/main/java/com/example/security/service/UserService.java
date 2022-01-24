package com.example.security.service;

import com.example.security.entity.Permission;
import com.example.security.entity.User;
import java.util.List;

public interface UserService {

  /**
   * 查询用户信息 - 通过用户名
   *
   * @param username 用户名
   * @return 用户信息
   */
  User selectByUsername(String username);

  /**
   * 查询用户权限信息 - 通过用户名
   *
   * @param username 用户名
   * @return 权限信息
   */
  List<Permission> selectPermissionsByUsername(String username);
}
