package com.example.springframework.boot.security.db.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.springframework.boot.security.db.mapper")
public class MybatisConfig {
}
