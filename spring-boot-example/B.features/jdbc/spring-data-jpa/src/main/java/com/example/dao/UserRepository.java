package com.example.dao;

import com.example.domain.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

  /* ---------- jpa根据方法名自动生成SQL语句 ---------- */

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
   * 查询用户信息 - 根据用户名模糊
   *
   * <p>注意，这里@Query中默认sql中的表名需为实体类的类名，字段名需为类中属性名
   *
   * @param username 用户名
   * @return /
   */
  @Query("select u from User u where u.username like %?1%")
  List<User> selectByUsernameLike(String username);

  /**
   * 查询用户信息 - 根据用户名密码
   *
   * <p>注意，这里@Query中默认sql中的表名需为实体类的类名，字段名需为类中属性名
   *
   * @param username 用户名
   * @param password 密码
   * @return /
   */
  @Query("select u from User u where u.username=:username and u.password=:password")
  User selectUsersByUsernameAndPassword(
      @Param(value = "username") String username, @Param(value = "password") String password);

  /**
   * 更新用户信息
   *
   * <p>nativeQuery = true，表示使用原生SQL
   *
   * @param id id
   * @param username 用户名
   * @param password 密码
   * @return /
   */
  @Transactional(rollbackFor = Exception.class)
  @Modifying
  @Query(value = "update t_user set username=?2,password=?3 where id=?1", nativeQuery = true)
  Integer updateUser(Long id, String username, String password);
}
