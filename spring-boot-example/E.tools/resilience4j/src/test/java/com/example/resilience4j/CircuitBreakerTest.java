package com.example.resilience4j;

import com.example.Resilience4jApplicationTests;
import com.example.modules.demo.service.CircuitBreakerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@DisplayName("断路器")
class CircuitBreakerTest extends Resilience4jApplicationTests {
  @Autowired CircuitBreakerService circuitBreakerService;

  @RepeatedTest(100) // 表示重复执行100次
  void testBackendA() {
    circuitBreakerService.backendA();
  }

  @RepeatedTest(100) // 表示重复执行100次
  void testBackendB() {
    circuitBreakerService.backendB();
  }

  @RepeatedTest(100) // 表示重复执行100次
  void testBackendC() {
    circuitBreakerService.backendC();
  }
}
