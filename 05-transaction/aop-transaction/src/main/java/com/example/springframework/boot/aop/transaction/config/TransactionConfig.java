package com.example.springframework.boot.aop.transaction.config;

import com.example.springframework.boot.aop.transaction.aop.DemoTransactionInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TransactionConfig {

    public static final String DEFAULT_TRANSACTION_MANAGER_NAME = "transactionManager";

    /* 注入bean实现方式：aspect */

    @Bean
    public DemoTransactionInterceptor demoTransactionInterceptor() {
        return new DemoTransactionInterceptor();
    }

    @Bean
    public Advisor demoTransactionAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * com.example.springframework.boot.aop.transaction.service..*.*(..))");
        DefaultPointcutAdvisor demoTransactionAdvisor = new DefaultPointcutAdvisor(pointcut, demoTransactionInterceptor());
        demoTransactionAdvisor.setOrder(1);
        return demoTransactionAdvisor;
    }

    /* 注入bean方式;动态代理 */

    //@Bean
    //public BeanNameAutoProxyCreator transactionAutoProxy() {
    //    BeanNameAutoProxyCreator autoProxy = new BeanNameAutoProxyCreator();
    //    autoProxy.setProxyTargetClass(true);
    //    autoProxy.setBeanNames("*Service");
    //    autoProxy.setInterceptorNames("demoTransactionInterceptor");
    //    autoProxy.setOrder(1);
    //    return autoProxy;
    //}
}
