package com.example.springframework.boot.multi.transaction.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment env;

    /* ----- 常规数据源配置 -----*/

    /*@Primary
    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "clusterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.cluster")
    public DataSource clusterDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "primaryTransactionManager")
    public DataSourceTransactionManager primaryTransactionManager() {
        return new DataSourceTransactionManager(primaryDataSource());
    }

    @Primary
    @Bean(name = "clusterTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager() {
        return new DataSourceTransactionManager(clusterDataSource());
    }*/

    /* ----- 使用分布式事务数据源配置 -----*/

    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource primaryDataSource() {
        //XADataSource
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(env.getProperty("spring.datasource.primary.jdbc-url"));
        mysqlXADataSource.setUser(env.getProperty("spring.datasource.primary.username"));
        mysqlXADataSource.setPassword(env.getProperty("spring.datasource.primary.password"));

        //atomikos
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXADataSource);
        xaDataSource.setUniqueResourceName("primaryDatasource");
        xaDataSource.setPoolSize(20);

        return xaDataSource;
    }

    @Bean(name = "clusterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.cluster")
    public DataSource clusterDataSource() {
        //XADataSource
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(env.getProperty("spring.datasource.cluster.jdbc-url"));
        mysqlXADataSource.setUser(env.getProperty("spring.datasource.cluster.username"));
        mysqlXADataSource.setPassword(env.getProperty("spring.datasource.cluster.password"));

        //atomikos
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXADataSource);
        xaDataSource.setUniqueResourceName("clusterDatasource");
        xaDataSource.setPoolSize(20);

        return xaDataSource;
    }

    /*
    引入JTA框架后不需要配置事务管理器，spring boot检测到jta环境会开启默认配置
    （其实是网上的自定义配置反作用，反而使jta事务管理配置失效，spring boot的官方文档上也只有提醒配置jta事务管理器id，所以暂不知道如何正确配置。。。）
     */

}
