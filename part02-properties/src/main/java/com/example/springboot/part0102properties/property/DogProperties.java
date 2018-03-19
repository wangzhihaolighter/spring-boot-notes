package com.example.springboot.part0102properties.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/1/2
 */
@Component
public class DogProperties {
    @Value("${dog.name}")
    private String name;

    @Value("${dog.age}")
    private Integer age;

    @Value("${dog.description}")
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
