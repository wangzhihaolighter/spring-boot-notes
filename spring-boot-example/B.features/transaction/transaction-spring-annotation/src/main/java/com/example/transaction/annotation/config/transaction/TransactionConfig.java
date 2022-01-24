package com.example.transaction.annotation.config.transaction;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    /*
    注解式事务配置方式：
    1.启用注释式事务配置
    2.配置事务管理器
    3.@Transactional注解需要开启事务的方法

    注解说明
        @EnableTransactionManagement：启用注释式事务配置
        @Transactional：类或方法开启事务
            value/transactionManager：指定事务管理器
            propagation：传播行为，默认Propagation.REQUIRED
            isolation：隔离级别，默认Isolation.DEFAULT
            timeout：事务超时时间，单位为秒。默认-1，表示没有时间限制。所谓事务超时，就是指一个事务所允许执行的最长时间，如果超过该时间限制但事务还没有完成，则自动回滚事务。
            readOnly:是否只读
                注意：
                    readonly并不是所有数据库都支持的，不同的数据库下会有不同的结果。
                    设置了readonly后，connection都会被赋予readonly，效果取决于数据库的实现。
                    在ORM中，设置了readonly会赋予一些额外的优化，例如在Hibernate中，会被禁止flush等。
            rollbackFor：导致事务回滚的异常类数组
            rollbackForClassName：导致事务回滚的异常类名字数组
            noRollbackFor：不会导致事务回滚的异常类数组
            noRollbackForClassName：不会导致事务回滚的异常类名字数组
                注意：
                    异常类必须继承自Throwable
                    rollback默认在遇到RuntimeException的时候会回滚，这个配置适用于指定需要回滚的非运行时异常
                    rollback指定RuntimeException子类，其他的RuntimeException仍会回滚
                    同理：noRollback适用于指定不需要回滚的运行时异常

    事务说明：
        由`org.springframework.transaction.TransactionDefinition`可知配置属性参数值

    注意：需要配置事务管理器，不指定，默认使用主事务管理器
    */

    public static final String DEFAULT_TRANSACTION_MANAGER_NAME = "transactionManager";

    @Bean(DEFAULT_TRANSACTION_MANAGER_NAME)
    @Primary
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
