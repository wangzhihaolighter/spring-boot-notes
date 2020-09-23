package com.example.springframework.boot.security.db.json.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.springframework.boot.security.db.json.mapper")
public class MybatisConfig {
}
