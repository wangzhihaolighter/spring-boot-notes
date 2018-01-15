package com.example.mybatis.part0302mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mybatis.part0302mybatis.mapper")
public class Part0302MybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(Part0302MybatisApplication.class, args);
	}
}
