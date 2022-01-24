package com.example.config;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

  // ==================== db1 ====================

  public static final String DATASOURCE_DB1 = "dataSourceDb1";

  @Primary
  @Bean(name = DATASOURCE_DB1)
  @ConfigurationProperties("spring.datasource.db1")
  public DataSource dataSourceDb1() {
    return DataSourceBuilder.create().build();
  }

  // ==================== db2 ====================

  public static final String DATASOURCE_DB2 = "dataSourceDb2";

  @Bean(name = DATASOURCE_DB2)
  @ConfigurationProperties("spring.datasource.db2")
  public DataSource dataSourceDb2() {
    return DataSourceBuilder.create().build();
  }
}
