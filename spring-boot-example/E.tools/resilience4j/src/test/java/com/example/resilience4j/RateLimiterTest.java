package com.example.resilience4j;

import com.example.Resilience4jApplicationTests;
import com.example.modules.demo.service.RateLimiterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@DisplayName("限流")
class RateLimiterTest extends Resilience4jApplicationTests {
  @Autowired RateLimiterService rateLimiterService;

  @RepeatedTest(10) // 表示重复执行10次
  void testBackendA() {
    rateLimiterService.backendA();
  }

  @RepeatedTest(10) // 表示重复执行10次
  void testBackendB() {
    rateLimiterService.backendB();
  }
}
