package com.example.springboot.part0402druid.common.config.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.example.springboot.part0402druid.common.constant.GlobalDatasourceConstant;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.primary")
    public DataSource dataSourcePrimary() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.secondary")
    public DataSource dataSourceSecond() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 动态数据源配置
     *
     * @return
     */
    @Bean
    public DynamicMultipleDataSource multipleDataSource(@Qualifier(GlobalDatasourceConstant.PRIMARY_DATA_SOURCE_KEY) DataSource dataSourcePrimary,
                                                        @Qualifier(GlobalDatasourceConstant.SECOND_DATA_SOURCE_KEY) DataSource dataSourceSecond) {
        DynamicMultipleDataSource dynamicMultipleDataSource = new DynamicMultipleDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(GlobalDatasourceConstant.PRIMARY_DATA_SOURCE_KEY, dataSourcePrimary);
        targetDataSources.put(GlobalDatasourceConstant.SECOND_DATA_SOURCE_KEY, dataSourceSecond);
        dynamicMultipleDataSource.setTargetDataSources(targetDataSources);
        dynamicMultipleDataSource.setDefaultTargetDataSource(dataSourcePrimary);
        return dynamicMultipleDataSource;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicMultipleDataSource dynamicMultipleDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicMultipleDataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DynamicMultipleDataSource dynamicMultipleDataSource) throws Exception {
        return new DataSourceTransactionManager(dynamicMultipleDataSource);
    }

    @Bean
    @Primary
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(DynamicMultipleDataSource dynamicMultipleDataSource) {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicMultipleDataSource);
        return sqlSessionFactoryBean;
    }


}
