package com.example.dao;

import com.example.domain.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  /* ---------- 根据方法名自动生成SQL语句 ---------- */

  /**
   * 查询用户信息 - 根据用户名密码
   *
   * @param username 用户名
   * @param password 密码
   * @return /
   */
  User findUsersByUsernameAndPassword(String username, String password);

  /**
   * 查询用户信息 - 根据用户名模糊
   *
   * @param username 用户名
   * @return /
   */
  List<User> findUsersByUsernameLike(String username);

  /**
   * 查询用户信息 - 根据ID倒序
   *
   * @return /
   */
  List<User> findUsersByIdNotNullOrderByIdDesc();

  /**
   * 查询用户信息 - 查询数量
   *
   * @return /
   */
  Integer countUsersByIdNotNull();

  /**
   * 查询用户信息 - 分页条件查询
   *
   * @param pageable /
   * @param username /
   * @return /
   */
  Page<User> queryByUsernameLike(Pageable pageable, String username);

  /**
   * 查询用户信息 - 查询结果排序
   *
   * @param sort /
   * @param username /
   * @return /
   */
  List<User> queryByUsernameLike(Sort sort, String username);

  /* ---------- 自定义SQL查询 ---------- */

  /**
   * 查询用户信息 - 根据用户名
   *
   * @param username 用户名
   * @return /
   */
  @Query("select * from T_USER where username = :username")
  List<User> selectByUsername(@Param("username") String username);

  /**
   * 更新用户信息
   *
   * @param id id
   * @param username 用户名
   * @param password 密码
   * @return /
   */
  @Transactional(rollbackFor = Exception.class)
  @Modifying
  @Query(value = "update T_USER set username = :username,password = :password where id = :id")
  Integer updateUser(
      @Param("id") Long id, @Param("username") String username, @Param("password") String password);
}
