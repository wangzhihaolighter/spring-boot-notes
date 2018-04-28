package com.example.springframework.boot.properties.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    /**
     * 获取配置文件中属性值得几种方式
     * 1.@Value注解
     * 2.@ConfigurationProperties
     * 3.@Autowired注入Environment
     * 注意：properties默认是GBK编码，获取中文会乱码,yml文件默认是utf-8编码，获取中文不会乱码
     */

    /**
     * 1.@Value注解
     */
    @Value("${user.name}")
    private String username;
    @Value("${user.description}")
    private String userDescription;

    @GetMapping("/value")
    public void getValue() {
        LOGGER.info("-----通过@Value获取-----");
        LOGGER.info(username);
        LOGGER.info(userDescription);
    }

    /**
     * 2.@ConfigurationProperties
     */
    @Component
    @ConfigurationProperties(prefix = "user")
    class User {
        private String name;
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    @Autowired
    private User user;

    @GetMapping("/configuration/properties")
    public void configurationProperties() {
        LOGGER.info("-----通过ConfigurationProperties获取-----");
        LOGGER.info(user.toString());
    }

    /**
     * 3.通过@Autowired注入Environment
     */
    @Autowired
    private Environment env;

    @GetMapping("/environment")
    public void environment() {
        LOGGER.info("-----通过@Autowired注入Environment获取-----");
        LOGGER.info(env.getProperty("user.name"));
        LOGGER.info(env.getProperty("user.description"));
    }

}
