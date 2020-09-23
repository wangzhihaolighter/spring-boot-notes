package com.example.springframework.boot.spring.annotation.transaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    /*
    注解说明
        @EnableTransactionManagement:启用注释式事务配置
        @Transactional:类或方法开启事务
            value/transactionManager:指定事务管理器
            propagation:传播行为,默认Propagation.REQUIRED
            isolation:指定隔离级别，默认Isolation.DEFAULT
            timeout:事务超时时间，单位为秒，默认-1，表示没有时间限制。所谓事务超时，就是指一个事务所允许执行的最长时间，如果超过该时间限制但事务还没有完成，则自动回滚事务。
            readOnly:是否只读,注意：
                - readonly并不是所有数据库都支持的，不同的数据库下会有不同的结果。
                - 设置了readonly后，connection都会被赋予readonly，效果取决于数据库的实现。
                - 在ORM中，设置了readonly会赋予一些额外的优化，例如在Hibernate中，会被禁止flush等。
            rollbackFor:导致事务回滚的异常类数组
            rollbackForClassName:导致事务回滚的异常类名字数组
            noRollbackFor:不会导致事务回滚的异常类数组
            noRollbackForClassName:不会导致事务回滚的异常类名字数组
            注意：
                异常类必须继承自Throwable
                rollback默认在遇到RuntimeException的时候会回滚,这个配置适用于指定需要回滚的非运行时异常
                rollback指定RuntimeException子类，其他的RuntimeException仍会回滚
                同理：noRollback适用于指定不需要回滚的运行时异常
    配置
        启用注释式事务配置
        配置事务管理器
        @Transactional注解需要开启事务的方法
    事务说明：
        隔离级别：org.springframework.transaction.annotation.Isolation枚举类中定义
            DEFAULT(-1) - 这是默认值，表示使用底层数据库的默认隔离级别。对大部分数据库而言，通常这值就是：READ_COMMITTED
            READ_UNCOMMITTED(1) - 该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据。该级别不能防止脏读和不可重复读，因此很少使用该隔离级别。
            READ_COMMITTED(2) - 该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。
            REPEATABLE_READ(4) - 该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。即使在多次查询之间有新增的数据满足该查询，这些新增的记录也会被忽略。该级别可以防止脏读和不可重复读。
            SERIALIZABLE(8) - 所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。
        传播行为：org.springframework.transaction.annotation.Propagation枚举类中定义
            REQUIRED(0) - 如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
            SUPPORTS(1) - 如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
            MANDATORY(2) - 如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
            REQUIRES_NEW(3) - 创建一个新的事务，如果当前存在事务，则把当前事务挂起。
            NOT_SUPPORTED(4) - 以非事务方式运行，如果当前存在事务，则把当前事务挂起。
            NEVER(5) - 以非事务方式运行，如果当前存在事务，则抛出异常。
            NESTED(6) - 如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于REQUIRED。

    注意：需要配置事务管理器，不指定，默认使用主事务管理器
    */

    public static final String DEFAULT_TRANSACTION_MANAGER_NAME = "transactionManager";

    @Bean(DEFAULT_TRANSACTION_MANAGER_NAME)
    @Primary
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
