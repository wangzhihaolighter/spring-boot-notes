package com.example.transaction.interceptor.service;

import com.example.transaction.interceptor.config.exception.InsertException;
import com.example.transaction.interceptor.config.exception.OtherException;
import com.example.transaction.interceptor.entity.User;
import java.util.List;

public interface UserService {

  /**
   * 正常查询
   *
   * @return 用户列表
   */
  List<User> query();

  /** 测试查询 */
  void queryReadOnlyTest();

  /**
   * 测试新增
   *
   * @throws InsertException 新增非运行时异常
   * @throws OtherException 其他非运行时异常
   */
  void saveTest() throws InsertException, OtherException;

  /** 测试更新 */
  void updateTest();

  /** 测试删除 */
  void deleteTest();
}
