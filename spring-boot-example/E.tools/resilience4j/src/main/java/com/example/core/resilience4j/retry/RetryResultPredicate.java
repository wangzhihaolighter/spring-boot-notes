package com.example.core.resilience4j.retry;

import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetryResultPredicate implements Predicate<Object> {

  @Override
  public boolean test(Object o) {
    log.info("根据结果判断是否需要重试");
    return true;
  }
}