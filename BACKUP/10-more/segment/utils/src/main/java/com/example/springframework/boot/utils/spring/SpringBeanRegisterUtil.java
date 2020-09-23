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
    /**
     * 获取ApplicationContext的几种方式
     * 1.手动创建：ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
     * <p>
     * 2.工具类继承抽象类ApplicationObjectSupport，并在工具类上使用@Component交由spring管理
     * <p>
     * 3.工具类继承抽象类WebApplicationObjectSupport，原理同2
     * <p>
     * 4.工具类实现ApplicationContextAware接口，并重写setApplicationContext(ApplicationContext applicationContext)方法，
     * 在工具类中使用@Component注解让spring进行管理。spring容器在启动的时候，会调用setApplicationContext()方法将ApplicationContext对象设置进去
     * <p>
     * 5.在web环境中通过spring提供的工具类获取，需要ServletContext对象作为参数。
     * 下面两个工具方式的区别是，前者在获取失败时返回null，后者抛出异常。
     * ApplicationContext context1 = WebApplicationContextUtils.getWebApplicationContext(ServletContext sc);
     * ApplicationContext context2 = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletContext sc);
     */
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
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

}