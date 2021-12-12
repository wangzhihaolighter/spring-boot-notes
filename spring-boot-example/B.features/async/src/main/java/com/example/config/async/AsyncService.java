package com.example.config.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Async
@Component
public class AsyncService {

  public void test() {
    log.info("AsyncService.test, thread: {}", Thread.currentThread().getName());
  }
}
