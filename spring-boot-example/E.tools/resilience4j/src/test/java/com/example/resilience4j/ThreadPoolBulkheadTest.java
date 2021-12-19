package com.example.resilience4j;

import com.example.Resilience4jApplicationTests;
import com.example.modules.demo.service.ThreadPoolBulkheadService;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@DisplayName("舱壁模式 - 固定线程池舱壁")
class ThreadPoolBulkheadTest extends Resilience4jApplicationTests {
  @Autowired ThreadPoolBulkheadService threadPoolBulkheadService;

  @RepeatedTest(10) // 表示重复执行10次
  void testBackendC() throws ExecutionException, InterruptedException {
    CompletableFuture<JsonNode> future = threadPoolBulkheadService.backendC();
    future.get();
  }
}
