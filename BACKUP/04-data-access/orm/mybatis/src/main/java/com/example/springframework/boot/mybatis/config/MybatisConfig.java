package com.example.springframework.boot.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.example.springframework.boot.mybatis.mapper")
public class MybatisConfig {
}
