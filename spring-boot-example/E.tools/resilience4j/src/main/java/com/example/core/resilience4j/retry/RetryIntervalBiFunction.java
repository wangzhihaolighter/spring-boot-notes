package com.example.core.resilience4j.retry;

import io.github.resilience4j.core.IntervalBiFunction;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetryIntervalBiFunction implements IntervalBiFunction<Object> {

  /**
   * 计算等待时间间隔
   *
   * @param integer 尝试次数(attempt)
   * @param integers 结果或异常
   * @return 待间隔(毫秒)
   */
  @Override
  public Long apply(Integer integer, Either<Throwable, Object> integers) {
    log.info("当前重试次数：{}", integer);
    return 2000L;
  }
}
