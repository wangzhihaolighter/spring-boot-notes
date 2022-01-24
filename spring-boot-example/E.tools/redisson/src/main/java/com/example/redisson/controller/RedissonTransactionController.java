package com.example.redisson.controller;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RMap;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 事务
 *
 * <p>Redisson为RMap、RMapCache、RLocalCachedMap、RSet、RSetCache和RBucket这样的对象提供了具有ACID属性的事务功能
 *
 * <p>Redisson事务通过分布式锁保证了连续写入的原子性，同时在内部通过操作指令队列实现了Redis原本没有的提交与滚回功能
 *
 * <p>当提交与滚回遇到问题的时候，将通过org.redisson.transaction.TransactionException告知用户
 */
@RestController
@RequestMapping("/tx")
public class RedissonTransactionController {

  private final RedissonClient redissonClient;

  public RedissonTransactionController(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  /** redisson事务参数配置 */
  TransactionOptions options =
      TransactionOptions.defaults()
          // 返回超时时间
          .responseTimeout(3, TimeUnit.SECONDS)
          // 重试次数
          .retryAttempts(3)
          // 重试间隔
          .retryInterval(1, TimeUnit.SECONDS)
          // 从节点同步超时时间
          .syncSlavesTimeout(5, TimeUnit.SECONDS)
          // 超时时间
          .timeout(5, TimeUnit.SECONDS);

  @RequestMapping("/test")
  public void test() {
    RTransaction transaction = redissonClient.createTransaction(options);
    try {
      RMap<String, String> map = transaction.getMap("myMap");
      for (int i = 0; i < 10; i++) {
        map.put(String.valueOf(i), UUID.randomUUID().toString());
      }
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    redissonClient.getMap("myMap").forEach((key, value) -> System.out.println(key + " : " + value));
  }

  @RequestMapping("/test/error")
  public void testError() {
    RTransaction transaction = redissonClient.createTransaction(options);
    try {
      RMap<String, String> map = transaction.getMap("myMap");
      map.clear();
      int i = 1 / 0;
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
    }
    redissonClient.getMap("myMap").forEach((key, value) -> System.out.println(key + " : " + value));
  }
}
