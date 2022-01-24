package com.example.rabbitmq.consumer.config;

import com.example.rabbitmq.consumer.receiver.DirectReceiver;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageListenerConfig {

    private final CachingConnectionFactory connectionFactory;
    private final DirectReceiver directReceiver; //Direct消息接收处理类
    private final DirectRabbitConfig directRabbitConfig;

    //    @Autowired
    //    FanoutReceiverA fanoutReceiverA; //Fanout消息接收处理类A

    //    @Autowired
    //    FanoutRabbitConfig fanoutRabbitConfig;

    public MessageListenerConfig(CachingConnectionFactory connectionFactory, DirectReceiver directReceiver, DirectRabbitConfig directRabbitConfig) {
        this.connectionFactory = connectionFactory;
        this.directReceiver = directReceiver;
        this.directRabbitConfig = directRabbitConfig;
    }


    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // RabbitMQ默认是自动确认，这里改为手动确认消息
        container.setQueues(directRabbitConfig.testDirectQueue());
        container.setMessageListener(directReceiver);
        //container.addQueues(fanoutRabbitConfig.queueA());
        //container.setMessageListener(fanoutReceiverA);
        return container;
    }
}