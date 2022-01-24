package com.example.service.impl;

import com.example.dao.db1.Test1Mapper;
import com.example.dao.db2.Test2Mapper;
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

  private final Test1Mapper test1Mapper;
  private final Test2Mapper test2Mapper;

  public TestServiceImpl(Test1Mapper test1Mapper, Test2Mapper test2Mapper) {
    this.test1Mapper = test1Mapper;
    this.test2Mapper = test2Mapper;
  }

  @Override
  public Map<String, Object> query() {
    Map<String, Object> map = new HashMap<>(2);
    List<Test1> test1List = test1Mapper.selectList(null);
    List<Test2> test2List = test2Mapper.selectList(null);
    map.put("test1", test1List);
    map.put("test2", test2List);
    return map;
  }

  @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
  @Override
  public void save() {
    Test1 test1 = new Test1();
    test1.setName("test1");
    test1Mapper.insert(test1);
    test1Mapper.selectList(null).forEach(System.out::println);

    Test2 test2 = new Test2();
    test2.setName("test2");
    test2Mapper.insert(test2);
    test2Mapper.selectList(null).forEach(System.out::println);
  }

  @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
  @Override
  public void saveError() {
    Test1 test1 = new Test1();
    test1.setName("error");
    test1Mapper.insert(test1);
    test1Mapper.selectList(null).forEach(System.out::println);

    Test2 test2 = new Test2();
    test2.setName("error");
    test2Mapper.insert(test2);
    test2Mapper.selectList(null).forEach(System.out::println);

    int i = 1 / 0;
  }
}
