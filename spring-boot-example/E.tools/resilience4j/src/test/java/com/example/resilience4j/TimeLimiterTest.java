package com.example.resilience4j;

import com.example.Resilience4jApplicationTests;
import com.example.modules.demo.service.TimeLimiterService;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@DisplayName("超时控制器")
class TimeLimiterTest extends Resilience4jApplicationTests {
  @Autowired TimeLimiterService timeLimiterService;

  @Test
  void testBackendA() throws ExecutionException, InterruptedException {
    CompletableFuture<JsonNode> future = timeLimiterService.backendA();
    future.get();
  }

  @Test
  void testBackendB() throws ExecutionException, InterruptedException {
    CompletableFuture<JsonNode> future = timeLimiterService.backendB();
    future.get();
  }
}
