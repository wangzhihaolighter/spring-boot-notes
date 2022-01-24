package com.example.elasticsearch.dao;

import com.example.elasticsearch.entity.User;
import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends ElasticsearchRepository<User, String> {

  /**
   * 查询用户 - 根据用户名
   *
   * @param username 用户名
   * @return 用户列表
   */
  List<User> findByUsername(String username);

  /**
   * 查询用户 - 根据用户年龄
   *
   * @param min 最小年龄
   * @param max 最大年龄
   * @return 用户列表
   */
  List<User> findByAgeBetween(Integer min, Integer max);

  /**
   * 查询用户 - 根据DSL查询
   *
   * @param username 用户名
   * @return 用户列表
   */
  @Query("{\"match\": {\"username\": {\"query\": \"?0\"}}}")
  List<User> selectByDsl(String username);
}
