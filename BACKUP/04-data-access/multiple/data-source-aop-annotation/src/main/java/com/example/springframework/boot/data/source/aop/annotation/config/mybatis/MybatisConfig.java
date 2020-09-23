package com.example.springframework.boot.data.source.aop.annotation.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.springframework.boot.data.source.aop.annotation.mapper")
public class MybatisConfig {
}
