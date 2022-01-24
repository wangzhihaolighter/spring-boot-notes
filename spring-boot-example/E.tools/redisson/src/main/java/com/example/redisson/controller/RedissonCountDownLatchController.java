package com.example.redisson.controller;

import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 闭锁 */
@RestController
@RequestMapping("/countDownLatch")
public class RedissonCountDownLatchController {

  private final RedissonClient redissonClient;

  public RedissonCountDownLatchController(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  /** 主线程等待所有子线程完成 */
  @SneakyThrows
  @RequestMapping("/await")
  public void await() {
    RCountDownLatch latch = redissonClient.getCountDownLatch("latch");
    // 设置计数器初始大小
    latch.trySetCount(3);
    long count = latch.getCount();
    System.out.println("count = " + count);
    // 阻塞线程直到计数器归零
    latch.await();
    System.out.println(Thread.currentThread().getName() + "所有子线程已运行完毕");
  }

  /** 子线程 */
  @SneakyThrows
  @RequestMapping("/thread")
  public void thread() {
    RCountDownLatch latch = redissonClient.getCountDownLatch("latch");
    System.out.println(Thread.currentThread().getName() + "抵达现场");
    TimeUnit.SECONDS.sleep(1);
    // 计数器减1，当计数器归零后通知所有等待着的线程恢复执行
    latch.countDown();
  }
}
