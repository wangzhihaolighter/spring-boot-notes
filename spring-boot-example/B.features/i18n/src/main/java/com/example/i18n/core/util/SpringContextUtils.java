package com.example.i18n.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {

  private static ApplicationContext applicationContext = null;

  public static <T> T getBean(Class<T> requiredType) {
    if (applicationContext != null) {
      return applicationContext.getBean(requiredType);
    } else {
      throw new org.springframework.context.ApplicationContextException(
          "ApplicationContext has not been set.");
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringContextUtils.applicationContext = applicationContext;
  }
}
