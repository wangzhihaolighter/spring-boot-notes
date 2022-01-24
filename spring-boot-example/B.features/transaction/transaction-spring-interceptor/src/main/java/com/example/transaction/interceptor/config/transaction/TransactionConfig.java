package com.example.transaction.interceptor.config.transaction;

import javax.sql.DataSource;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/** 事务配置 */
@Configuration
public class TransactionConfig {

  /** 自定义事务拦截器 */
  @Bean
  public TransactionInterceptor customTransactionInterceptor() {
    return new TransactionInterceptor();
  }

  /** 事务管理器 */
  @Primary
  @Bean("transactionManager")
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  /** service添加事务拦截器 */
  @Bean
  public Advisor customTransactionAdvisor() {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression(
        "execution(public * com.example.transaction.interceptor.service..*.*(..))");
    return new DefaultPointcutAdvisor(pointcut, customTransactionInterceptor());
  }
}
