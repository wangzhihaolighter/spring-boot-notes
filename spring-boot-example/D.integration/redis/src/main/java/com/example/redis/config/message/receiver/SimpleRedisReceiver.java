package com.example.redis.config.message.receiver;

import org.springframework.stereotype.Component;

@Component
public class SimpleRedisReceiver {

  public void receiveMessage(String message) {
    System.out.println("Received a message：" + message);
  }
}
