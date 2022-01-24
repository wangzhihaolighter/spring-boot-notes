package com.example.config;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseConfigDb1 {
  private final DataSource dataSource;

  public LiquibaseConfigDb1(@Qualifier(DataSourceConfig.DATASOURCE_DB1) DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean(name = "springLiquibaseDb1")
  public SpringLiquibase springLiquibaseDb1() {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog("classpath:/db/changelog/db1/master.xml");
    // 注意：这项配置会在启动时清空指定数据源
    liquibase.setDropFirst(true);
    return liquibase;
  }
}
