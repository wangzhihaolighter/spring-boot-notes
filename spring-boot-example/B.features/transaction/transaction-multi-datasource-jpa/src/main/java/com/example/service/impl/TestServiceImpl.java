package com.example.service.impl;

import com.example.dao.db1.Test1Repository;
import com.example.dao.db2.Test2Repository;
import com.example.entity.db1.Test1;
import com.example.entity.db2.Test2;
import com.example.service.TestService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

  private final Test1Repository test1Repository;
  private final Test2Repository test2Repository;

  public TestServiceImpl(Test1Repository test1Repository, Test2Repository test2Repository) {
    this.test1Repository = test1Repository;
    this.test2Repository = test2Repository;
  }

  @Override
  public Map<String, Object> query() {
    Map<String, Object> map = new HashMap<>(2);
    List<Test1> test1List = test1Repository.findAll();
    List<Test2> test2List = test2Repository.findAll();
    map.put("test1", test1List);
    map.put("test2", test2List);
    return map;
  }

  @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
  @Override
  public void save() {
    Test1 test1 = new Test1();
    test1.setName("test1");
    test1Repository.save(test1);
    test1Repository.findAll().forEach(System.out::println);

    Test2 test2 = new Test2();
    test2.setName("test2");
    test2Repository.save(test2);
    test2Repository.findAll().forEach(System.out::println);
  }

  @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
  @Override
  public void saveError() {
    Test1 test1 = new Test1();
    test1.setName("error");
    test1Repository.save(test1);
    test1Repository.findAll().forEach(System.out::println);

    Test2 test2 = new Test2();
    test2.setName("error");
    test2Repository.save(test2);
    test2Repository.findAll().forEach(System.out::println);

    int i = 1 / 0;
  }
}
