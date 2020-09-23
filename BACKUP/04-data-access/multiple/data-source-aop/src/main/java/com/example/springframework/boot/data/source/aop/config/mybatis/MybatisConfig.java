package com.example.springframework.boot.data.source.aop.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.example.springframework.boot.data.source.aop.mapper")
public class MybatisConfig {
}
