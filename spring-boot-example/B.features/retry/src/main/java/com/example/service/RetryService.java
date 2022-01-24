package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RetryService {

  /**
   * 测试方法
   *
   * @throws IllegalAccessException /
   */
  @Retryable(
      // 可重试的异常类型。 包括（）的同义词。 默认为空（并且如果 excludes 也为空，则重试所有异常）。
      value = IllegalAccessException.class,
      // 最大尝试次数(包括第一次失败)
      maxAttempts = 5,
      backoff =
          @Backoff(
              // 延迟（毫秒）
              value = 1000,
              // 重试之间的最大延迟（毫秒）
              maxDelay = 100000,
              // 用于计算下一个回退延迟的乘数(默认 0 = 忽略)
              multiplier = 1.5))
  public void service() throws IllegalAccessException {
    log.info("service method...");
    throw new IllegalAccessException("manual exception");
  }

  /** 重试失败，执行这个方法 */
  @Recover
  public void recover(IllegalAccessException e) {
    log.info("service retry after Recover => " + e.getMessage());
  }
}
