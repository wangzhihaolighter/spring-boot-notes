package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.service.TestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
  private final ObjectMapper objectMapper;
  private final Gson gson;

  public TestServiceImpl(ObjectMapper objectMapper, Gson gson) {
    this.objectMapper = objectMapper;
    this.gson = gson;
  }

  @Override
  public String jackson(Object target) throws JsonProcessingException {
    return objectMapper.writeValueAsString(target);
  }

  @Override
  public String gson(Object target) {
    return gson.toJson(target);
  }

  @Override
  public String fastjson(Object target) {
    return JSON.toJSONString(target);
  }
}
