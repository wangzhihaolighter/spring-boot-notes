package com.example.redisson.controller;

import javax.annotation.PostConstruct;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流器测试
 *
 * <p>1.init方法生成令牌
 *
 * <p>2.通过该限流器的名称来获取令牌limiter.tryAcquire()
 *
 * <p>3.获取令牌的执行,否则返回未获取的提示信息,可以用于秒杀场景
 *
 * @author wangzhihao
 */
@RestController
@RequestMapping("/limiter")
public class RedissonRateLimiterController {

  private static final String RATE_LIMITER_NAME = "rateLimiter";

  private final RedissonClient redissonClient;

  public RedissonRateLimiterController(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  /** */
  @PostConstruct
  public void init() {
    // 初始化限流器
    RRateLimiter limiter = redissonClient.getRateLimiter(RATE_LIMITER_NAME);
    // 每 rateInterval 产生 rate 个令牌
    limiter.setRate(RateType.PER_CLIENT, 3, 5, RateIntervalUnit.SECONDS);
  }

  /** 获取令牌 */
  @GetMapping("/acquire")
  public String thread() {
    RRateLimiter limiter = redissonClient.getRateLimiter(RATE_LIMITER_NAME);
    // 尝试获取1个令牌
    if (limiter.tryAcquire(1)) {
      return "获取到令牌，剩余令牌数量：" + limiter.availablePermits();
    }
    return "未获取到令牌";
  }
}
