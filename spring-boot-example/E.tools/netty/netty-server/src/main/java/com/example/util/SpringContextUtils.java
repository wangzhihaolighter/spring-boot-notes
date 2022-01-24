package com.example.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring上下文工具类
 *
 * @author wangzhihao
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
  private static ApplicationContext applicationContext;

  /** 实现ApplicationContextAware接口的回调方法，设置上下文环境 */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    SpringContextUtils.applicationContext = applicationContext;
  }

  /**
   * 获得spring上下文
   *
   * @return ApplicationContext spring上下文
   */
  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  /**
   * 获取bean
   *
   * @param name service注解方式name为小驼峰格式
   * @return Object bean的实例对象
   */
  public static Object getBean(String name) throws BeansException {
    return applicationContext.getBean(name);
  }

  /**
   * 获取bean
   *
   * @param clazz bean类
   * @return Object bean的实例对象
   */
  public static <T> T getBean(Class<T> clazz) throws BeansException {
    return applicationContext.getBean(clazz);
  }

  /**
   * 获取bean
   *
   * @param name service注解方式name为小驼峰格式
   * @param clazz bean类
   * @return Object bean的实例对象
   */
  public static <T> T getBean(String name, Class<T> clazz) throws BeansException {
    return applicationContext.getBean(name, clazz);
  }
}
