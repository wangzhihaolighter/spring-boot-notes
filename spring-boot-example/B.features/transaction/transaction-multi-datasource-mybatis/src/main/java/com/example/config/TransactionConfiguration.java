package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 事务配置类，Atomikos自动配置JTA，默认事务管理器beanName：transactionManager
 *
 * <p>JTA Configuration for <A href="https://www.atomikos.com/">Atomikos</a>.
 *
 * @see org.springframework.boot.autoconfigure.transaction.jta.AtomikosJtaConfiguration
 * @author wangzhihao
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfiguration {}
