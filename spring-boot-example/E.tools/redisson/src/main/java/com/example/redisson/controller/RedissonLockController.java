package com.example.redisson.controller;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分布式锁
 *
 * @author wangzhihao
 */
@Slf4j
@RestController
@RequestMapping("/lock")
public class RedissonLockController {
  private static final String LOCK_KEY = "lock";

  private final RedissonClient redissonClient;

  public RedissonLockController(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  /** 没获取到锁阻塞线程 */
  @GetMapping("/block")
  public String block() {
    String result = "未获取到锁";
    RLock lock = null;
    try {
      // 创建一个名字为lock的锁，如果是并发访问,会阻塞到lock.lock()
      lock = redissonClient.getLock(LOCK_KEY);
      lock.lock();
      result = "获取到锁";
      TimeUnit.SECONDS.sleep(30);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      e.printStackTrace();
    } finally {
      unlock(lock);
    }
    return result;
  }

  /** 立刻返回获取锁的状态 */
  @GetMapping("/directly")
  public String tryDirectly() {
    String result = "未获取到锁";
    RLock lock = null;
    try {
      lock = redissonClient.getLock(LOCK_KEY);
      // 判断获取锁,执行业务逻辑,否则直接返回提示信息
      if (lock.tryLock()) {
        result = "获取到锁";
        TimeUnit.SECONDS.sleep(3);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      e.printStackTrace();
    } finally {
      unlock(lock);
    }
    return result;
  }

  /** 立刻返回获取锁的状态 */
  @GetMapping("/wait")
  public String tryWait() {
    String result = "未获取到锁";

    // 公平锁,按照先后顺序依次分配锁
    // RLock lock=redissonClient.getFairLock(LOCK_KEY);

    // 非公平锁,随机取一个等待中的线程分配锁
    RLock lock = redissonClient.getLock(LOCK_KEY);

    try {
      // 最多等待锁waitTime，leaseTime后强制解锁,推荐使用
      if (lock.tryLock(2, 10, TimeUnit.SECONDS)) {
        result = "获取到锁";
        TimeUnit.SECONDS.sleep(3);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      e.printStackTrace();
    } finally {
      unlock(lock);
    }
    return result;
  }

  private void unlock(RLock lock) {
    if (null != lock && lock.isHeldByCurrentThread()) {
      lock.unlock();
    }
  }
}
