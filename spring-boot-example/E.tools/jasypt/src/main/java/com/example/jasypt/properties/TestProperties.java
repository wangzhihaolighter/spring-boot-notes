package com.example.jasypt.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@ToString
@Component
@ConfigurationProperties("test")
public class TestProperties implements InitializingBean {
    private String encProperty;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this);
    }
}
