package com.example.config.validation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validator;

@Configuration
public class ValidationConfig {

    /*
    hibernate的校验模式:
        普通模式（默认）：校验所有的属性，然后返回所有的验证失败信息
        快速失败返回模式：只要有一个验证失败，则返回
     */

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LocalValidatorFactoryBean defaultValidator(@Qualifier("validationMessageResource") MessageSource messageSource) {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        //设置validator模式：快速失败返回
        localValidatorFactoryBean.getValidationPropertyMap().put("hibernate.validator.fail_fast", "true");
        //设置提示消息国际化
        //方式一
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        //方式二
        //localValidatorFactoryBean.setMessageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("i18n/SystemValidationMessages")));
        return localValidatorFactoryBean;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(Environment environment, @Lazy Validator validator) {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        boolean proxyTargetClass = environment.getProperty("spring.aop.proxy-target-class", Boolean.class, true);
        processor.setProxyTargetClass(proxyTargetClass);
        processor.setValidator(validator);
        return postProcessor;
    }

}
