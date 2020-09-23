package com.example.springframework.boot.aop.transaction.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.example.springframework.boot.aop.transaction.mapper")
public class MybatisConfig {
}