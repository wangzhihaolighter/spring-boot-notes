package com.example.redis.config.message;

import com.example.redis.config.message.receiver.RedisChannelMessageReceiver;
import com.example.redis.config.message.receiver.SimpleRedisReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RedisListenerConfig {

    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     *
     * @param connectionFactory /
     * @return /
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        //可以添加多个 messageListener
        container.addMessageListener(simpleListenerAdapter(), new PatternTopic(RedisListenerChannelConstant.CHANNEL_SIMPLE));
        List<Topic> topicList = new ArrayList<>();
        topicList.add(new PatternTopic(RedisListenerChannelConstant.CHANNEL_WELCOME));
        topicList.add(new PatternTopic(RedisListenerChannelConstant.CHANNEL_USER));
        container.addMessageListener(channelMessageListenerAdapter(), topicList);

        return container;
    }


    /*
    消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     */

    @Bean
    MessageListenerAdapter simpleListenerAdapter() {
        return new MessageListenerAdapter(new SimpleRedisReceiver(), "receiveMessage");
    }

    @Bean
    MessageListenerAdapter channelMessageListenerAdapter() {
        return new MessageListenerAdapter(new RedisChannelMessageReceiver());
    }

}

