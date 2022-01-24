package com.example.service.impl;

import com.example.config.dynamic.DynamicDataSourceConstant;
import com.example.config.dynamic.annotation.DS;
import com.example.dao.TestRepository;
import com.example.entity.Test;
import com.example.service.DynamicDataSourceService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DynamicDataSourceServiceImpl implements DynamicDataSourceService {

  private final TestRepository testRepository;

  public DynamicDataSourceServiceImpl(TestRepository testRepository) {
    this.testRepository = testRepository;
  }

  @DS(DynamicDataSourceConstant.DB_KEY_DB1)
  @Override
  public List<Test> queryDb1() {
    return testRepository.findAll();
  }

  @DS(DynamicDataSourceConstant.DB_KEY_DB2)
  @Override
  public List<Test> queryDb2() {
    return testRepository.findAll();
  }

  @DS(DynamicDataSourceConstant.DB_KEY_DB1)
  @Override
  public void saveDb1() {
    Test test = new Test();
    test.setName("db1");
    testRepository.save(test);
  }

  @DS(DynamicDataSourceConstant.DB_KEY_DB2)
  @Override
  public void saveDb2() {
    Test test = new Test();
    test.setName("db2");
    testRepository.save(test);
  }
}
