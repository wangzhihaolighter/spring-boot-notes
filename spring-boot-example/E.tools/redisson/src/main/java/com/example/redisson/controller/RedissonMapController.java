package com.example.redisson.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryExpiredListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** map测试 */
@Slf4j
@RestController
@RequestMapping("/map")
public class RedissonMapController {

  private static final String KEY = "my_test_map";
  private final RedissonClient redissonClient;

  public RedissonMapController(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  /** 初始化Listener，仅初始化一次，过期事件不一定那么及时触发，存在一定的延时 注意如果触发2次,则会执行2次回调 */
  @PostConstruct
  public void init() {
    RMapCache<String, String> map = redissonClient.getMapCache(KEY);
    map.addListener(
        (EntryExpiredListener<String, String>)
            event ->
                log.info(
                    "{}已过期,原来的值为:{},现在的值为:{}",
                    event.getKey(),
                    event.getOldValue(),
                    event.getValue()));
    log.info("{}初始化完成", KEY);
  }

  /** 存放Key-Value对 */
  @GetMapping("/put")
  public String put(String a, String b, boolean flag) {
    RMapCache<String, String> map = redissonClient.getMapCache(KEY);
    if (flag) {
      // key设置有效时间,并在失效时触发上面的监听函数
      map.put(a, b, 5, TimeUnit.SECONDS);
    } else {
      map.put(a, b);
    }
    return String.format("%s - 设置 %s = %s 成功", LocalDateTime.now(), a, b);
  }

  /** 遍历map中的所以元素,需要指定key */
  @GetMapping("/show")
  public Map<String, String> get() {
    return redissonClient.getMapCache(KEY);
  }
}
