package com.example.modules.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import java.util.concurrent.CompletableFuture;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 舱壁模式 - 固定线程池舱壁，只对CompletableFuture方法有效
 *
 * @author wangzhihao
 */
@Slf4j
@Service
public class ThreadPoolBulkheadService {
  private final ObjectMapper mapper;

  public ThreadPoolBulkheadService(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @SneakyThrows
  @Bulkhead(name = "backendC", fallbackMethod = "fallback", type = Type.THREADPOOL)
  public CompletableFuture<JsonNode> backendC() {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            Thread.sleep(1000L);
          } catch (InterruptedException e) {
            log.warn("Interrupted!", e);
            Thread.currentThread().interrupt();
          }

          ObjectNode res = mapper.createObjectNode();
          res.put("msg", "backendC success");
          log.info("backendC: {}", res);
          return res;
        });
  }

  private CompletableFuture<JsonNode> fallback(BulkheadFullException exception) {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", exception.getLocalizedMessage());
    log.error("fallback: {}", res);
    return CompletableFuture.supplyAsync(() -> res);
  }
}
