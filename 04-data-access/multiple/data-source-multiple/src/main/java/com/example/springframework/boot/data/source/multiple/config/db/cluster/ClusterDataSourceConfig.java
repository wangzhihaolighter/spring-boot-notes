package com.example.springframework.boot.data.source.multiple.config.db.cluster;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = ClusterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class ClusterDataSourceConfig {

    static final String PACKAGE = "com.example.springframework.boot.data.source.multiple.mapper.cluster";
    private static final String MAPPER_LOCATION = "classpath:mybatis/mapper/cluster/*.xml";

    @Autowired
    private Environment env;

    @Bean(name = "clusterDataSource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.cluster.driver-class-name"));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.cluster.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.cluster.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.cluster.password"));
        return dataSource;
    }

    @Bean(name = "clusterTransactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "clusterSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
