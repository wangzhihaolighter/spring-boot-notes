package com.example.bean;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 项目配置属性bean
 */
@Data
@Component
public class PropertiesBean implements InitializingBean {
    //Spring Boot配置属性

    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${server.port}")
    private String serverPort;

    // 操作系统参数

    @Value("${username}")
    private String username;
    @Value("${JAVA_HOME}")
    private String javaHome;

    //自定义参数

    @Value("${my.name}")
    private String myName;
    @Value("${my.age}")
    private Integer age;
    @Value("${my.description}")
    private String myDescription;


    @Override
    public void afterPropertiesSet() {
        System.out.println(this.toString());
    }
}
