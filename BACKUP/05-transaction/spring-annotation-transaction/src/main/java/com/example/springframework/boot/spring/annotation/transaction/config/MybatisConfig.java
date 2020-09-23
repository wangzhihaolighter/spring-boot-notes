package com.example.springframework.boot.spring.annotation.transaction.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.example.springframework.boot.spring.annotation.transaction.mapper")
public class MybatisConfig {
}