package com.example.redisson.controller;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RList;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * collection测试：list、queue
 *
 * @author wangzhihao
 */
@RestController
@RequestMapping("/collection")
public class RedissonCollectionController {

  private final RedissonClient redissonClient;

  public RedissonCollectionController(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  @PostConstruct
  public void acceptElement() {
    RBlockingQueue<String> queue = redissonClient.getBlockingQueue("my_blocking_queue");
    // 注意： subscribeOnElements 用 takeAsync() 实现的，这里获取后就相当于在队列中获取并删除这个元素
    queue.subscribeOnElements(s -> System.out.println("my_blocking_queue获取到元素:" + s));
  }

  /** List测试 - 添加元素 */
  @GetMapping("/list/add")
  public List<String> addList(String a) {
    RList<String> list = redissonClient.getList("my_list");
    list.add(a);
    return list.readAll();
  }

  /** List测试 - 删除元素 */
  @GetMapping("/list/remove")
  public List<String> removeList(String a) {
    RList<String> list = redissonClient.getList("my_list");
    // 删除集合中所有指定元素
    list.removeAll(Collections.singletonList(a));
    return list.readAll();
  }

  /** Queue测试 - 添加元素 */
  @GetMapping("/queue/add")
  public List<String> addQueue(String a) {
    RQueue<String> queue = redissonClient.getQueue("my_queue");
    queue.add(a);
    return queue.readAll();
  }

  /** Queue测试 - 读取元素 */
  @GetMapping("/queue/poll")
  public String pollQueue() {
    RQueue<String> queue = redissonClient.getQueue("my_queue");
    // 从队列的头部获取一个元素并从队列中删除该元素，队列为空时返回null
    return queue.poll();
  }

  /** Blocking Queue测试 - 添加元素 */
  @GetMapping("/blocking/add")
  public List<String> addBlockingQueue(String a) {
    RBlockingQueue<String> blockingQueue = redissonClient.getBlockingQueue("my_blocking_queue");
    blockingQueue.add(a);
    return blockingQueue.readAll();
  }

  /** Blocking Queue测试 - 读取元素 */
  @GetMapping("/blocking/get")
  public String getBlockingQueue() throws InterruptedException {
    RBlockingQueue<String> blockingQueue = redissonClient.getBlockingQueue("my_blocking_queue");
    // 从队列的头部获取一个元素并从队列中删除该元素，队列为空时返回null
    // return blockingQueue.poll();
    // 从队列的头部获取一个元素但不删除该元素，队列为空时返回null
    // return blockingQueue.peek();
    // 从队列的头部获取一个元素并从队列中删除该元素，队列为空时阻塞线程
    return blockingQueue.take();
  }

  /** Delayed Queue测试 - 添加元素 */
  @GetMapping("/delayed/add")
  public List<String> addDelayedQueue(String element, Long delay) {
    // 目标队列
    RQueue<String> queue = redissonClient.getQueue("my_blocking_queue");
    // 延迟队列，数据临时存放地，发出后删除该元素
    RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(queue);
    delayedQueue.offer(element, delay, TimeUnit.SECONDS);
    return delayedQueue.readAll();
  }
}
