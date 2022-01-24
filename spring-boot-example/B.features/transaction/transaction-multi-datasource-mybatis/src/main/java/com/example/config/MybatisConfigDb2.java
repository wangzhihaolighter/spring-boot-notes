package com.example.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(
    basePackages = MybatisConfigDb2.PACKAGE,
    sqlSessionFactoryRef = MybatisConfigDb2.SQL_SESSION_FACTORY_REF_DB2)
public class MybatisConfigDb2 {

  public static final String PACKAGE = "com.example.dao.db2";
  public static final String MAPPER_LOCATION = "classpath:mybatis/mapper/db2/*.xml";
  public static final String SQL_SESSION_FACTORY_REF_DB2 = "sqlSessionFactoryDb2";

  private final DataSource dataSource;

  public MybatisConfigDb2(@Qualifier(DataSourceConfig.DATASOURCE_DB2) DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean(name = SQL_SESSION_FACTORY_REF_DB2)
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    // 纯粹的mybatis使用：final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
    Resource[] resources = new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION);
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setMapperLocations(resources);
    return sessionFactory.getObject();
  }
}
