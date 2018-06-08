package com.example.springframework.boot.async.transaction.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.example.springframework.boot.async.transaction.mapper")
public class MybatisConfig {
}
