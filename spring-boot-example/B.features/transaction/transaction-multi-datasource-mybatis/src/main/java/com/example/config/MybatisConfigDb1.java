package com.example.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(
    basePackages = MybatisConfigDb1.PACKAGE,
    sqlSessionFactoryRef = MybatisConfigDb1.SQL_SESSION_FACTORY_REF_DB1)
public class MybatisConfigDb1 {

  static final String PACKAGE = "com.example.dao.db1";
  static final String MAPPER_LOCATION = "classpath:mybatis/mapper/db1/*.xml";
  static final String SQL_SESSION_FACTORY_REF_DB1 = "sqlSessionFactoryDb1";

  private final DataSource dataSource;

  public MybatisConfigDb1(@Qualifier(DataSourceConfig.DATASOURCE_DB1) DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Primary
  @Bean(name = SQL_SESSION_FACTORY_REF_DB1)
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    // 纯粹的mybatis使用：final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
    Resource[] resources = new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION);
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setMapperLocations(resources);
    return sessionFactory.getObject();
  }
}
