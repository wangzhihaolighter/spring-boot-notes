package com.example.resilience4j;

import com.example.Resilience4jApplicationTests;
import com.example.modules.demo.service.BulkheadService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@DisplayName("舱壁模式 - 信号量舱壁")
class BulkheadTest extends Resilience4jApplicationTests {
  @Autowired BulkheadService bulkheadService;

  @RepeatedTest(10) // 表示重复执行10次
  void testBackendA() {
    bulkheadService.backendA();
  }

  @RepeatedTest(10) // 表示重复执行10次
  void testBackendB() {
    bulkheadService.backendB();
  }
}
