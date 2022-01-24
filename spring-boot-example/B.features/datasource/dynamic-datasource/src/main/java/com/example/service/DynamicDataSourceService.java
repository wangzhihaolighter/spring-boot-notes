package com.example.service;

import com.example.entity.Test;
import java.util.List;

public interface DynamicDataSourceService {

  /**
   * 查询：数据源1
   *
   * @return /
   */
  List<Test> queryDb1();

  /**
   * 查询：数据源2
   *
   * @return /
   */
  List<Test> queryDb2();

  /** 新增：数据源1 */
  void saveDb1();

  /** 新增：数据源2 */
  void saveDb2();
}
