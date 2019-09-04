package com.example.bean;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "my.like")
@PropertySource(value = "classpath:config/simple.yml", factory = YamlPropertySourceFactory.class)
public class SimpleYaml3 implements InitializingBean {
    private String food;
    private List<String> pc;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.toString());
    }
}
