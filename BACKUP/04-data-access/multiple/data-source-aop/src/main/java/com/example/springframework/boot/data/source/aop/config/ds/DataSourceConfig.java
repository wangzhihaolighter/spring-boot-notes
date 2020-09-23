package com.example.springframework.boot.data.source.aop.config.ds;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    /**
     * 步骤
     * 1.创建两个数据源
     * 2.创建动态数据源,并设置primary
     * 3.注入动态数据源的aspect处理
     */

    @Autowired
    private Environment env;

    @Bean(DataSourceKeyConstant.MASTER)
    public DataSource masterDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.master.driver-class-name"));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.master.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.master.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.master.password"));
        return dataSource;
    }

    @Bean(DataSourceKeyConstant.SLAVE)
    public DataSource slaveDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.slave.driver-class-name"));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.slave.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.slave.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.slave.password"));
        return dataSource;
    }

    /**
     * 动态数据源配置
     */
    @Primary
    @Bean
    public DynamicDataSource dataSource() {
        DynamicDataSource dynamicMultipleDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        //设置多个数据源
        targetDataSources.put(DataSourceKeyConstant.MASTER, masterDataSource());
        targetDataSources.put(DataSourceKeyConstant.SLAVE, slaveDataSource());
        dynamicMultipleDataSource.setTargetDataSources(targetDataSources);
        //默认为主库
        dynamicMultipleDataSource.setDefaultTargetDataSource(masterDataSource());
        return dynamicMultipleDataSource;
    }

    /**
     * service方法前动态设置数据源
     */
    @Bean
    public DataSourceAspect dataSourceAspect() {
        return new DataSourceAspect();
    }

    //TODO 多数据源的事务管理，保证一致性回滚

}
