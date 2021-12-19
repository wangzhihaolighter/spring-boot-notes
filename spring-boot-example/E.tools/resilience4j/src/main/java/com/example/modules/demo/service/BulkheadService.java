package com.example.modules.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 舱壁模式 - 信号量舱壁
 *
 * @author wangzhihao
 */
@Slf4j
@Service
public class BulkheadService {
  private final ObjectMapper mapper;

  public BulkheadService(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @SneakyThrows
  @Bulkhead(name = "backendA", fallbackMethod = "fallback")
  public JsonNode backendA() {
    Thread.sleep(5000L);
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", "backendA success");
    log.info("backendA: {}", res);
    return res;
  }

  @SneakyThrows
  @Bulkhead(name = "backendB", fallbackMethod = "fallback", type = Bulkhead.Type.SEMAPHORE)
  public JsonNode backendB() {
    Thread.sleep(5000L);
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", "backendB success");
    log.info("backendB: {}", res);
    return res;
  }

  private JsonNode fallback(BulkheadFullException exception) {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", exception.getLocalizedMessage());
    log.error("fallback: {}", res);
    return res;
  }
}
