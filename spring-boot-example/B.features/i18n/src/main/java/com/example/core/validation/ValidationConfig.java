package com.example.core.validation;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

/**
 * 参数校验配置
 *
 * @author wangzhihao
 */
@Configuration
public class ValidationConfig {

  @Bean
  public Validator getValidator() {
    final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames("i18n/validationMessages");
    LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
    // 使用 HibernateValidator
    validatorFactoryBean.setProviderClass(HibernateValidator.class);
    // 本地化消息
    validatorFactoryBean.setValidationMessageSource(messageSource);
    // 快速失败
    validatorFactoryBean.getValidationPropertyMap().put("hibernate.validator.fail_fast", "true");
    return validatorFactoryBean;
  }
}
