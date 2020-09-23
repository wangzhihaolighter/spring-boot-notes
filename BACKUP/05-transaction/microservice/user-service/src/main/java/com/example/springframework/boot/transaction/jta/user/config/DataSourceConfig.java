package com.example.springframework.boot.transaction.jta.user.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    /* ----- 使用分布式事务数据源配置 -----*/

    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource primaryDataSource() {
        //XADataSource
        JdbcDataSource xaDataSource = new JdbcDataSource();
        xaDataSource.setUrl("jdbc:h2:mem:test");
        xaDataSource.setUser("sa");
        xaDataSource.setPassword("");

        //atomikos
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(xaDataSource);
        atomikosDataSourceBean.setUniqueResourceName("userServiceDatasource");
        atomikosDataSourceBean.setPoolSize(20);

        return atomikosDataSourceBean;
    }

    /*
    引入JTA框架后不需要配置事务管理器，spring boot检测到jta环境会开启默认配置
    （其实是网上的自定义配置反作用，反而使jta事务管理配置失效，spring boot的官方文档上也只有提醒配置jta事务管理器id，所以暂不知道如何正确配置。。。）
     */

}
