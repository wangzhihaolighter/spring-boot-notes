package com.example.redis.controller;

import com.example.redis.config.RedisHelper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

  private final RedisHelper redisHelper;

  public RedisController(RedisHelper redisHelper) {
    this.redisHelper = redisHelper;
  }

  /*redis基本操作 - 写入/获取/删除/其他*/

  @PutMapping("/database/reset")
  public void resetDataBase(@RequestParam("database") int database) {
    redisHelper.resetDataBase(database);
  }

  @PostMapping("/set")
  public Boolean set(
      @RequestParam("key") String key,
      @RequestParam("value") String value,
      @RequestParam("expireTime") Long expireTime,
      @RequestParam("database") int database) {
    redisHelper.resetDataBase(database);
    return redisHelper.set(key, value, expireTime);
  }

  @GetMapping("/get")
  public Object get(@RequestParam("key") String key, @RequestParam("database") Integer database) {
    redisHelper.resetDataBase(database);
    return redisHelper.get(key);
  }

  @DeleteMapping("/remove")
  public Boolean remove(
      @RequestParam("key") String key, @RequestParam("database") Integer database) {
    redisHelper.resetDataBase(database);
    return redisHelper.remove(key);
  }

  @GetMapping("/exists")
  public Boolean exists(
      @RequestParam("key") String key, @RequestParam("database") Integer database) {
    redisHelper.resetDataBase(database);
    return redisHelper.exists(key);
  }
}
