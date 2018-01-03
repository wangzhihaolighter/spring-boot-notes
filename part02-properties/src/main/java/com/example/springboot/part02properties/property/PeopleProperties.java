package com.example.springboot.part02properties.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/1/2
 */
@Component
@ConfigurationProperties(prefix = "people")
public class PeopleProperties {
    private String name;

    private Integer age;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
