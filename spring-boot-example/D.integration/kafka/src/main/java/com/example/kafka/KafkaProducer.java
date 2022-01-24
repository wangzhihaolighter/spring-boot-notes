package com.example.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Component
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(KafkaConstant.TOPIC_TEST, message);
    }

    public void sendMessageAddCallback(String message) {
        kafkaTemplate.send(KafkaConstant.TOPIC_TEST, message).addCallback(
                success -> {
                    // 消息发送到的topic
                    String topic = success.getRecordMetadata().topic();
                    // 消息发送到的分区
                    int partition = success.getRecordMetadata().partition();
                    // 消息在分区内的offset
                    long offset = success.getRecordMetadata().offset();
                    System.out.printf("发送消息成功: 主题[%s] , 分区[%d] , 偏移[%d]%n", topic, partition, offset);
                },
                failure -> {
                    System.out.println("发送消息失败: " + failure.getMessage());
                });
    }

    public void sendMessageTransaction(String message) {
        kafkaTemplate.send(KafkaConstant.TOPIC_TEST, message);
        int i = 1 / 0;
    }

}