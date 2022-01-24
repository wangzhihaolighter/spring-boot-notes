package com.example.mybatis.pagehelper.mapper;

import com.example.mybatis.pagehelper.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  /**
   * 查询所有用户信息
   *
   * @return /
   */
  List<User> selectAll();
}
