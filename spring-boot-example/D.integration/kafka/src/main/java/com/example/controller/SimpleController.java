package com.example.controller;

import com.example.kafka.KafkaProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
    private final KafkaProducer kafkaProducer;

    public SimpleController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/")
    public String sayHello() {
        return "Hello,World!";
    }

    @GetMapping("/kafka")
    public void sendMessage(@RequestParam("message") String message) {
        kafkaProducer.sendMessage(message);
    }

    @GetMapping("/kafka/callback")
    public void sendMessageAddCallback(@RequestParam("message") String message) {
        kafkaProducer.sendMessageAddCallback(message);
    }

    @GetMapping("/kafka/transaction")
    public void sendMessageTransaction(@RequestParam("message") String message) {
        try {
            kafkaProducer.sendMessageTransaction(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}