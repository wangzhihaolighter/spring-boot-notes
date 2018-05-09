package com.example.springframework.boot.mybatis.generator.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.example.springframework.boot.mybatis.generator.mapper")
public class MybatisConfig {
}
