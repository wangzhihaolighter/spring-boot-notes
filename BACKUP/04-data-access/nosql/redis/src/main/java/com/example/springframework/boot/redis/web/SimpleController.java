package com.example.springframework.boot.redis.web;

import com.example.springframework.boot.redis.config.redis.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimpleController {

    @Autowired
    private RedisHelper redisHelper;

    @GetMapping("/")
    public String home() {
        return "hello redis";
    }

    /*redis基本操作 - 写入/获取/删除/其他*/

    @PutMapping("/redis/database/reset")
    public void resetDataBase(@RequestParam("database") int database) {
        redisHelper.resetDataBase(database);
    }

    @PostMapping("/redis/set")
    public Boolean set(@RequestParam("key") String key,
                       @RequestParam("value") String value,
                       @RequestParam("expireTime") Long expireTime,
                       @RequestParam("database") int database) {
        redisHelper.resetDataBase(database);
        return redisHelper.set(key, value, expireTime);
    }

    @GetMapping("/redis/get")
    public Object get(@RequestParam("key") String key,
                      @RequestParam("database") Integer database) {
        redisHelper.resetDataBase(database);
        return redisHelper.get(key);
    }

    @DeleteMapping("/redis/remove")
    public Boolean remove(@RequestParam("key") String key,
                          @RequestParam("database") Integer database) {
        redisHelper.resetDataBase(database);
        return redisHelper.remove(key);
    }

    @GetMapping("/redis/exists")
    public Boolean exists(@RequestParam("key") String key,
                          @RequestParam("database") Integer database) {
        redisHelper.resetDataBase(database);
        return redisHelper.exists(key);
    }

}
