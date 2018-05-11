package com.example.springframework.boot.data.source.aop.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class DataSourceAopAnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataSourceAopAnnotationApplication.class, args);
    }
}
