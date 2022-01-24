package com.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface TestService {

  /**
   * jackson 测试
   *
   * @param target /
   * @return /
   * @throws JsonProcessingException /
   */
  String jackson(Object target) throws JsonProcessingException;

  /**
   * gson 测试
   *
   * @param target /
   * @return /
   */
  String gson(Object target);

  /**
   * fastjson 测试
   *
   * @param target /
   * @return /
   */
  String fastjson(Object target);
}
