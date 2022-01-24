package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = {KafkaConstant.TOPIC_TEST})
    public void onMessage(ConsumerRecord<?, ?> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.printf("消息消费：主题[%s] , 分区[%d] , 消息[%s]%n", record.topic(), record.partition(), record.value());
    }
}