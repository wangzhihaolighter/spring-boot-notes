package com.example.springboot.part0402druid;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Part0402DruidApplication {

    public static void main(String[] args) {
        SpringApplication.run(Part0402DruidApplication.class, args);
    }

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     * 设置 dev test 环境开启
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
