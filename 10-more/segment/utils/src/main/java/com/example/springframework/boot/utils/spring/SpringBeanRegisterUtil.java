package com.example.springframework.boot.utils.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * springApplicationContext工具
 */
public class SpringBeanRegisterUtil {
    private static ApplicationContext context = new
            ClassPathXmlApplicationContext("applicationContext.xml");
    private static ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) context;
    private static BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();

    /**
     * 注册bean
     *
     * @param beanId    所注册bean的id
     * @param className bean的className，
     *                  三种获取方式：1、直接书写，如：com.mvc.entity.User
     *                  2、User.class.getName
     *                  3.user.getClass().getName()
     */
    public static void registerBean(String beanId, String className) {
        // get the BeanDefinitionBuilder
        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(className);
        // get the BeanDefinition
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // register the bean
        beanDefinitionRegistry.registerBeanDefinition(beanId, beanDefinition);
    }

    /**
     * 移除bean
     *
     * @param beanId bean的id
     */
    public static void unregisterBean(String beanId) {
        beanDefinitionRegistry.removeBeanDefinition(beanId);
    }

    /**
     * 获取bean
     *
     * @param name bean的id
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }
}