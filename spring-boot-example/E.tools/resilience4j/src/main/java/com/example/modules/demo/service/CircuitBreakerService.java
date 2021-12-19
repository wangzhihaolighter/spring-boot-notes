package com.example.modules.demo.service;

import com.example.core.exception.BusinessRecordException;
import com.example.core.exception.OtherRuntimeException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 断路器
 *
 * @author wangzhihao
 */
@Slf4j
@Service
public class CircuitBreakerService {
  private final ObjectMapper mapper;

  public CircuitBreakerService(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @CircuitBreaker(name = "backendA", fallbackMethod = "fallback")
  public JsonNode backendA() {
    throw new BusinessRecordException("backendA error");
  }

  @CircuitBreaker(name = "backendB", fallbackMethod = "fallback")
  public JsonNode backendB() {
    throw new BusinessRecordException("backendB error");
  }

  @CircuitBreaker(name = "backendC", fallbackMethod = "fallback")
  public JsonNode backendC() {
    throw new OtherRuntimeException("backendC error");
  }

  private JsonNode fallback(CallNotPermittedException exception) {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", exception.getLocalizedMessage());
    log.error("fallback: {}", res);
    return res;
  }
}
