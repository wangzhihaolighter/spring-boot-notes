package com.example.core.scheduling.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** 用于测试调度 */
@Slf4j
@Component("TestTask")
public class TestTask {

  public void test() {
    log.info("test");
  }

  @SneakyThrows
  public void testLock() {
    log.info("testLock");
    Thread.sleep(3000L);
  }
}
