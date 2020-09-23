package com.example.springframework.boot.mybatis.page.helper.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.example.springframework.boot.mybatis.page.helper.mapper")
public class MybatisConfig {
}
