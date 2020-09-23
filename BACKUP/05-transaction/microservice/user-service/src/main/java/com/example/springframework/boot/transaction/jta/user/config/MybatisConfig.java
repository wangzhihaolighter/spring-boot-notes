package com.example.springframework.boot.transaction.jta.user.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = MybatisConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfig {

    static final String PACKAGE = "com.example.springframework.boot.transaction.jta.user.mapper";
    private static final String MAPPER_LOCATION = "classpath:mybatis/mapper/*.xml";

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource dataSource;

    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
