package com.example.springboot.part0601redis;

import com.example.springboot.part0601redis.entity.City;
import com.example.springboot.part0601redis.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Part0601RedisApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test() throws JsonProcessingException {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        // 保存字符串
        User user = new User("1", "菜狗子", 3);
        City city = new City("2", "上海", "美丽又充满活力的城市");
        ops.set(user.getId(), mapper.writeValueAsString(user), 1000, TimeUnit.SECONDS);
        ops.set(city.getId(), mapper.writeValueAsString(city), 1000, TimeUnit.SECONDS);
        System.out.println(ops.get(user.getId()));
        System.out.println(ops.get(city.getId()));
    }

}
