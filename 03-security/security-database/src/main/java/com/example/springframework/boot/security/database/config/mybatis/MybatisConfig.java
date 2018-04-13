package com.example.springframework.boot.security.database.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.springframework.boot.security.database.mapper")
public class MybatisConfig {
}
