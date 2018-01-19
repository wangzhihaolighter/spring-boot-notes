package com.example.springboot.part0305mybatismapperpagehelper;

import com.example.springboot.part0305mybatismapperpagehelper.util.CommonMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

@SpringBootApplication
public class Part0305MybatisMapperPagehelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(Part0305MybatisMapperPagehelperApplication.class, args);
    }

    /**
     * 注册通用mapper
     *
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.example.springboot.part0305mybatismapperpagehelper.mapper");
        Properties properties = new Properties();
        // 这里要特别注意，不要把MyMapper放到 basePackage 中，也就是不能同其他Mapper一样被扫描到。
        properties.setProperty("mappers", CommonMapper.class.getName());
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
}
