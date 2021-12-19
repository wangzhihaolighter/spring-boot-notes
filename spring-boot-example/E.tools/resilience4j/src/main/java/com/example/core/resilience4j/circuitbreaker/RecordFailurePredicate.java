package com.example.core.resilience4j.circuitbreaker;

import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RecordFailurePredicate implements Predicate<Throwable> {

  @Override
  public boolean test(Throwable throwable) {
    log.error("自定义断言 - 根据异常判断是否是失败记录，异常信息：{}", throwable.getMessage());
    return true;
  }
}