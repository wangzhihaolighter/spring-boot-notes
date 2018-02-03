package com.example.springboot.part0402druid;

import com.example.springboot.part0402druid.common.config.datasource.MyDatasourceAop;
import com.example.springboot.part0402druid.common.config.transaction.MyTransactionInterceptor;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//这里抛弃springboot的启动注解事务注解,因为这个无法修改事务拦截的执行顺序，永远比动态数据源的切面先执行
//@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
public class Part0402DruidApplication {

    public static void main(String[] args) {
        SpringApplication.run(Part0402DruidApplication.class, args);
    }

    @Bean(name = "myTransactionInterceptor")
    public MyTransactionInterceptor myTransactionInterceptor() {
        return new MyTransactionInterceptor();
    }

    @Bean(name = "myDatasourceAop")
    public MyDatasourceAop myDatasourceAop() {
        return new MyDatasourceAop();
    }

    /**
     * 代理
     *
     * @return
     */
    @Bean
    public BeanNameAutoProxyCreator transactionAutoProxy() {
        BeanNameAutoProxyCreator autoProxy = new BeanNameAutoProxyCreator();
        autoProxy.setProxyTargetClass(true);// 这个属性为true时，表示被代理的是目标类本身而不是目标类的接口
        //注意，拦截器的顺序依赖于名字字符串传入的先后顺序，@Order什么的是完全没用的，因为保存这些拦截器的是一个字符串数组
        autoProxy.setBeanNames("*ServiceImpl");
        autoProxy.setInterceptorNames("myDatasourceAop", "myTransactionInterceptor");
        return autoProxy;
    }
}
