package com.example.modules.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 超时
 *
 * @author wangzhihao
 */
@Slf4j
@Service
public class TimeLimiterService {
  private final ObjectMapper mapper;

  public TimeLimiterService(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @TimeLimiter(name = "backendA", fallbackMethod = "fallback")
  public CompletableFuture<JsonNode> backendA() {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            Thread.sleep(10 * 1000L);
          } catch (InterruptedException e) {
            log.warn("Interrupted!", e);
            Thread.currentThread().interrupt();
          }

          ObjectNode res = mapper.createObjectNode();
          res.put("msg", "backendA success");
          log.info("backendA: {}", res);
          return res;
        });
  }

  @SneakyThrows
  @TimeLimiter(name = "backendB", fallbackMethod = "fallback")
  public CompletableFuture<JsonNode> backendB() {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            Thread.sleep(10 * 1000L);
          } catch (InterruptedException e) {
            log.warn("Interrupted!", e);
            Thread.currentThread().interrupt();
          }

          ObjectNode res = mapper.createObjectNode();
          res.put("msg", "backendB success");
          log.info("backendB: {}", res);
          return res;
        });
  }

  private CompletableFuture<ObjectNode> fallback(TimeoutException exception) {
    return CompletableFuture.supplyAsync(
        () -> {
          ObjectNode res = mapper.createObjectNode();
          res.put("msg", exception.getLocalizedMessage());
          log.error("fallback: {}", res);
          return res;
        });
  }
}
