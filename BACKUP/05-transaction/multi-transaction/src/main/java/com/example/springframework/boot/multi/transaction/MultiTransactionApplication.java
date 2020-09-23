package com.example.springframework.boot.multi.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class MultiTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiTransactionApplication.class, args);
    }
}
