package com.example.springboot.part0308mybatismultipledatasources.config.ds;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
@MapperScan(basePackages = SecondDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "secondSqlSessionFactory")
public class SecondDataSourceConfig {

    static final String PACKAGE = "com.example.springboot.part0308mybatismultipledatasources.mapper.second";
    static final String MAPPER_LOCATION = "classpath:mybatis/mapper/second/*.xml";

    @Value("${second.datasource.url}")
    private String url;

    @Value("${second.datasource.driverClassName}")
    private String driverClassName;

    @Value("${second.datasource.username}")
    private String username;

    @Value("${second.datasource.password}")
    private String password;

    @Bean(name = "secondDataSource")
    public DataSource secondDataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "secondTransactionManager")
    public DataSourceTransactionManager secondTransactionManager() {
        return new DataSourceTransactionManager(secondDataSource());
    }

    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(secondDataSource());
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SecondDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
