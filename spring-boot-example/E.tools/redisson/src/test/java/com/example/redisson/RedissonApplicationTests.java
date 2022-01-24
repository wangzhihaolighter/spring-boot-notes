package com.example.redisson;

import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedissonApplicationTests {

  @Autowired RedissonClient redissonClient;

  @SneakyThrows
  @Test
  void testLimiter() {
    // 初始化限流器
    RRateLimiter limiter = redissonClient.getRateLimiter("rateLimiter2");
    // 每 rateInterval 产生 rate 个令牌
    limiter.setRate(RateType.OVERALL, 3, 3, RateIntervalUnit.SECONDS);
    TimeUnit.SECONDS.sleep(5);
    for (int i = 0; i < 10; i++) {
      // RRateLimiter limiterInstance = redissonClient.getRateLimiter("rateLimiter");
      // 尝试获取1个令牌
      if (limiter.tryAcquire(1)) {
        System.out.println("获取到令牌，剩余令牌数量：" + limiter.availablePermits());
      } else {
        System.out.println("未获取到令牌");
      }
    }
  }
}
