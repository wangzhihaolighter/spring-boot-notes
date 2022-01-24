package com.example.redisson.controller;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RSet;
import org.redisson.api.RSetCache;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 计分排序集测试
 *
 * @author wangzhihao
 */
@Slf4j
@RestController
@RequestMapping("/scoredSortedSet")
public class RedissonScoredSortedSetController {

  private final RedissonClient redissonClient;

  public RedissonScoredSortedSetController(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  @GetMapping("/add")
  public String addScore(String element, Double score) {
    // 创建Set
    RScoredSortedSet<String> scoredSortedSet = redissonClient.getScoredSortedSet("scoredSortedSet");
    // 设置过期时间
    boolean exists = scoredSortedSet.isExists();
    if (!exists) {
      scoredSortedSet.expire(30, TimeUnit.SECONDS);
    }
    scoredSortedSet.addListener((ExpiredObjectListener) name -> log.info("超时事件被触发，name={}", name));
    // 添加元素
    scoredSortedSet.add(score, element);
    // 获取元素在集合中的位置
    Integer index = scoredSortedSet.revRank(element);
    log.info("size={},element={},index={},score={}", scoredSortedSet.size(), element, index, score);

    // 可以设置元素过期，但是不能触发对应过期事件
    RSetCache<String> setCache = redissonClient.getSetCache("scoredSortedSet2");
    setCache.add(element, 30, TimeUnit.SECONDS);
    // 可设置,但不会触发监听.
    setCache.addListener((ExpiredObjectListener) name -> log.info("超时事件被触发，event={}", name));

    // 不能设置元素过期
    RSet<String> set = redissonClient.getSet("scoredSortedSet3");
    set.add(element);

    return "ok";
  }

  @GetMapping("/show")
  public String showList(String key) {
    log.info("排行榜 = {}", key);
    RScoredSortedSet<String> set = redissonClient.getScoredSortedSet(key);
    set.stream()
        .forEach(
            element -> {
              Integer index = set.revRank(element); // 获取元素在集合中的位置
              Double score = set.getScore(element); // 获取元素的评分
              log.info("size={},element={},index={},score={}", set.size(), element, index, score);
            });
    return "ok";
  }

}
