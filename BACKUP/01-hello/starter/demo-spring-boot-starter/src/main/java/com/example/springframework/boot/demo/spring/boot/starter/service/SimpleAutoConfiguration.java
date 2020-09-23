package com.example.springframework.boot.demo.spring.boot.starter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(SimpleService.class)
@EnableConfigurationProperties(SimpleServiceProperties.class)
public class SimpleAutoConfiguration {

    /*
    Starter的工作原理
        Spring Boot在启动时扫描项目所依赖的JAR包，寻找包含spring.factories文件的JAR包
        根据spring.factories配置加载AutoConfigure类
        根据@Conditional注解的条件，进行自动配置并将Bean注入Spring Context


    注解说明:
        @Configuration：说明该类是配置类
        @EnableConfigurationProperties，开启属性注入
        @AutoConfigureAfter：指定在指定配置类之后进行自动配置
        @AutoConfigureBefore，指定在指定配置类之前进行自动配置
        @ConditionalOnClass，当classpath下发现该类的情况下进行自动配置。
        @ConditionalOnMissingBean，当Spring Context中不存在该Bean时进行自动配置。
        @ConditionalOnProperty(prefix = "simple.service",value = "enabled",havingValue = "true")，当配置文件中simple.service.enabled=true时进行自动配置。
        @AutoConfigureOrder,该注释具有与常规@Order注释相同的语义，但为自动配置类提供了专用的顺序。

    spring.factories中存在多个配置时中间用 ,\ 分隔(org.springframework.boot.autoconfigure.EnableAutoConfiguration=\ 由此配置指定)
    官方文档：https://docs.spring.io/spring-boot/docs/2.0.2.RELEASE/reference/html/boot-features-developing-auto-configuration.html
    */

    @Autowired
    private SimpleServiceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "simple.service", value = "enabled", havingValue = "true")
    SimpleService simpleService() {
        return new SimpleService(properties.getPrefix(), properties.getSuffix());
    }

}
