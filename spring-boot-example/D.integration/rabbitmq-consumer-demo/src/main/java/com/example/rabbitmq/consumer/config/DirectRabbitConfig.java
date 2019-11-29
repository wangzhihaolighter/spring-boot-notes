package com.example.rabbitmq.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {

    @Bean
    public Queue testDirectQueue() {
        //队列 起名：TestDirectQueue
        return new Queue("TestDirectQueue", true);  //true 是否持久
    }

    @Bean
    DirectExchange testDirectExchange() {
        //Direct交换机 起名：TestDirectExchange
        return new DirectExchange("TestDirectExchange");
    }

    @Bean
    Binding bindingDirect() {
        //绑定 - 将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
        return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with("TestDirectRouting");
    }

}