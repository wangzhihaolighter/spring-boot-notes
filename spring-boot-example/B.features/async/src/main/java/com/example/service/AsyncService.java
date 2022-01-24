package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Async
@Component
public class AsyncService {

  public void test() {
    log.info("service - 可通过日志输出中的线程名判断是否异步");
  }
}
