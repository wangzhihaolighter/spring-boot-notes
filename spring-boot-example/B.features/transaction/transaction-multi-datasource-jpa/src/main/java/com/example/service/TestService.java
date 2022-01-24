package com.example.service;

import java.util.Map;

public interface TestService {
  /**
   * 查询
   *
   * @return /
   */
  Map<String, Object> query();

  /** 新增：两个数据源中新增数据 */
  void save();

  /** 新增：两个数据源中新增数据，发生异常 */
  void saveError();
}
