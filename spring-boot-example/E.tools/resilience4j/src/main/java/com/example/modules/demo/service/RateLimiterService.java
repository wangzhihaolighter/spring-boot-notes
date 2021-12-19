package com.example.modules.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 限流
 *
 * @author wangzhihao
 */
@Slf4j
@Service
public class RateLimiterService {
  private final ObjectMapper mapper;

  public RateLimiterService(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @RateLimiter(name = "backendA", fallbackMethod = "fallback")
  public JsonNode backendA() {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", "backendA success");
    log.info("backendA: {}", res);
    return res;
  }

  @RateLimiter(name = "backendB", fallbackMethod = "fallback")
  public JsonNode backendB() {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", "backendB success");
    log.info("backendB: {}", res);
    return res;
  }

  private JsonNode fallback(RequestNotPermitted exception) {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", exception.getLocalizedMessage());
    log.error("fallback: {}", res);
    return res;
  }
}
