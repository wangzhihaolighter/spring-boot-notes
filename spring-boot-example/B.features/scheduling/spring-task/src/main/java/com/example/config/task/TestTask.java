package com.example.config.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestTask {

  /** initialDelay毫秒后开始执行，方法每fixedRate毫秒执行一次 */
  @Scheduled(initialDelay = 1000, fixedRate = 5000)
  public void test1() {
    log.info("test1");
  }

  /** initialDelay毫秒后开始执行，方法执行结束后fixedDelay豪秒再重复执行 */
  @SneakyThrows
  @Scheduled(initialDelay = 2000, fixedDelay = 5000)
  public void test2() {
    log.info("test2");
    Thread.sleep(5000L);
  }

  /** 从0分0秒开始，每分钟执行一次 zone时区：ZoneId.systemDefault() */
  @Scheduled(cron = "0 0/1 * * * ?", zone = "GMT+8")
  public void test3() {
    log.info("test3");
  }
}
