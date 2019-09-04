package com.example.bean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentBean implements InitializingBean {
    @Autowired
    private Environment environment;

    @Override
    public void afterPropertiesSet() throws Exception {
        String appName = environment.getProperty("spring.application.name");
        Integer serverPort = environment.getProperty("server.port", Integer.class);
        System.out.println("spring.application.name=" + appName);
        System.out.println("server.port=" + serverPort);
    }
}
