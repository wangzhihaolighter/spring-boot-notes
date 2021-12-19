package com.example.resilience4j;

import com.example.Resilience4jApplicationTests;
import com.example.modules.demo.service.RetryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@DisplayName("重试器")
class RetryTest extends Resilience4jApplicationTests {
  @Autowired RetryService retryService;

  @Test
  void testBackendA() {
    retryService.backendA();
  }

  @Test
  @DisplayName("忽略指定异常，不重试")
  void testBackendB() {
    retryService.backendB();
  }

  @Test
  @DisplayName("根据异常类型判断是否重试")
  void testBackendC() {
    retryService.backendC();
  }

  @Test
  @DisplayName("根据结果判断是否需要重试")
  void backendD() {
    retryService.backendD();
  }

  @Test
  @DisplayName("重试时间间隔 - 自定义间隔策略")
  void backendE() {
    retryService.backendE();
  }

  @Test
  @DisplayName("重试时间间隔 - 指数退避抖动")
  void backendF() {
    retryService.backendF();
  }

  @Test
  @DisplayName("重试时间间隔 - 随机延迟")
  void backendG() {
    retryService.backendG();
  }
}
