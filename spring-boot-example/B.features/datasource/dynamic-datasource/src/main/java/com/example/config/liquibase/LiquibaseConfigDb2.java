package com.example.config.liquibase;

import com.example.config.datasource.DataSourceConfig;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseConfigDb2 {
  private final DataSource dataSource;

  public LiquibaseConfigDb2(@Qualifier(DataSourceConfig.DATASOURCE_DB2) DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean(name = "springLiquibaseDb2")
  public SpringLiquibase springLiquibaseDb2() {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog("classpath:/db/changelog/db2/master.xml");
    // 注意：这项配置会在启动时清空指定数据源
    liquibase.setDropFirst(true);
    return liquibase;
  }
}
