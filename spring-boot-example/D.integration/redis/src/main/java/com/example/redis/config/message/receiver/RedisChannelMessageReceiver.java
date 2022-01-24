package com.example.redis.config.message.receiver;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisChannelMessageReceiver implements MessageListener {

  @Override
  public void onMessage(Message message, byte[] pattern) {
    // 消息体
    System.out.println("消息体：" + new String(message.getBody()));
    // 频道名
    System.out.println("频道名：" + new String(message.getChannel()));
    // 模式
    System.out.println("模式：" + new String(pattern));
  }
}
