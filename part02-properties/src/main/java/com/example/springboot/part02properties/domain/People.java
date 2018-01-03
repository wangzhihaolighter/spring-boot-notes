package com.example.springboot.part02properties.domain;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/1/3
 */
public class People {
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
