package com.example.springframework.boot.mybatis.mapper.generator.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = "com.example.springframework.boot.mybatis.mapper.generator.mapper")
public class MybatisConfig {
    //TODO 注意，这里使用的@MapperScan需是通用mapper包的
}
