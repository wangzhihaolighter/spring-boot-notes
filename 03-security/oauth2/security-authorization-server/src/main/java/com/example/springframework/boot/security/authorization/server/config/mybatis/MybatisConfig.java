package com.example.springframework.boot.security.authorization.server.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.springframework.boot.security.authorization.server.mapper")
public class MybatisConfig {
}
