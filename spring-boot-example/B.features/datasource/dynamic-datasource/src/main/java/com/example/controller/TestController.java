package com.example.controller;

import com.example.entity.Test;
import com.example.service.DynamicDataSourceService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
  private final DynamicDataSourceService dynamicDataSourceService;

  public TestController(DynamicDataSourceService dynamicDataSourceService) {
    this.dynamicDataSourceService = dynamicDataSourceService;
  }

  @GetMapping
  public Map<String, Object> query() {
    Map<String, Object> map = new HashMap<>(2);
    List<Test> db1List = dynamicDataSourceService.queryDb1();
    List<Test> db2List = dynamicDataSourceService.queryDb2();
    map.put("db1", db1List);
    map.put("db2", db2List);
    return map;
  }

  @PostMapping
  public void save() {
    dynamicDataSourceService.saveDb1();
    dynamicDataSourceService.saveDb2();
  }

  @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
  @PostMapping("/error")
  public void saveError() {
    dynamicDataSourceService.saveDb1();
    dynamicDataSourceService.saveDb2();
    int i = 1 / 0;
  }
}
