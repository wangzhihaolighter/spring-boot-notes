package com.example.core.resilience4j.retry;

import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetryExceptionPredicate implements Predicate<Throwable> {

  @Override
  public boolean test(Throwable throwable) {
    log.error("根据异常判断是否需要重试，异常信息：{}", throwable.getMessage());
    return true;
  }
}