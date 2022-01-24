package com.example.mybatis.mapper.mapper;

import com.example.mybatis.mapper.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends io.mybatis.mapper.Mapper<User, Long> {
  /**
   * 查询用户信息 - 通过用户名模糊查询
   *
   * @param username 用户名
   * @return 用户信息
   */
  List<User> queryByUsernameLike(@Param("username") String username);
}
