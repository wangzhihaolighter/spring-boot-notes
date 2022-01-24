package com.example.redisson.controller;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 话题(订阅分发)
 *
 * @author wangzhihao
 */
@Slf4j
@RestController
@RequestMapping("/topic")
public class RedissonTopicController {

  private final RedissonClient redissonClient;

  public RedissonTopicController(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  /** 订阅 */
  @PostConstruct
  public void consume() {
    // 订阅指定话题
    log.info("订阅话题：{}", "anyTopic");
    RTopic topic = redissonClient.getTopic("anyTopic");
    topic.addListener(String.class, (charSequence, msg) -> System.out.println("接收到消息：" + msg));
  }

  /** 分发 http://127.0.0.1:8080/topic/produce?a=redis主题 */
  @GetMapping("/produce")
  public String produce(String a) {
    RTopic topic = redissonClient.getTopic("anyTopic");
    topic.publish(a);
    return "发送消息：" + a;
  }
}
