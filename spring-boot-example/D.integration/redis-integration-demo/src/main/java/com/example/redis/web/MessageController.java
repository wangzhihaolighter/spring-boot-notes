package com.example.redis.web;

import com.example.redis.config.RedisHelper;
import com.example.redis.config.message.RedisListenerChannelConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final RedisHelper redisHelper;

    public MessageController(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @GetMapping("/send")
    public ResponseEntity<Boolean> sendMessage(@RequestParam(value = "channel", required = false, defaultValue = RedisListenerChannelConstant.CHANNEL_SIMPLE) String topic,
                                               @RequestParam(value = "message", required = false, defaultValue = "hello") String message) {
        redisHelper.convertAndSend(topic, message);
        return ResponseEntity.ok(true);
    }

}
