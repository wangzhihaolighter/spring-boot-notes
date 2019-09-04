package com.example.bean;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class SimpleYaml2 implements InitializingBean {
    private Map<String, Object> object = loadYaml();

    private static Map<String, Object> loadYaml() {
        YamlMapFactoryBean yaml = new YamlMapFactoryBean();
        yaml.setResources(new ClassPathResource("config/simple.yml"));
        return yaml.getObject();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.toString());
    }
}
