package com.example.dao;

import com.example.domain.User;
import java.util.List;

public interface UserDao {
  /**
   * 查询用户信息 - 所有
   *
   * @return /
   */
  List<User> selectAll();

  /**
   * 查询用户信息 - 根据用户ID
   *
   * @param id 用户ID
   * @return /
   */
  User selectById(Long id);

  /**
   * 新增用户
   *
   * @param user 用户信息
   * @return 用户ID
   */
  Long insert(User user);

  /**
   * 更新用户
   *
   * @param user 用户信息
   * @return 影响记录数
   */
  Integer update(User user);

  /**
   * 删除用户
   *
   * @param id 用户ID
   * @return 影响记录数
   */
  Integer delete(Long id);

  /**
   * 批量删除用户
   *
   * @param ids 用户ID集合
   * @return 影响记录数
   */
  Integer batchDelete(List<Long> ids);
}
