package com.example.config.dynamic;

import com.example.config.datasource.DataSourceConfig;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/** 动态数据源配置 */
@Configuration
public class DynamicDataSourceConfig {
  private final DataSource dataSourceDb1;
  private final DataSource dataSourceDb2;

  public DynamicDataSourceConfig(
      @Qualifier(DataSourceConfig.DATASOURCE_DB1) DataSource dataSourceDb1,
      @Qualifier(DataSourceConfig.DATASOURCE_DB2) DataSource dataSourceDb2) {
    this.dataSourceDb1 = dataSourceDb1;
    this.dataSourceDb2 = dataSourceDb2;
  }

  /** 动态数据源作为主数据源 */
  @Primary
  @Bean
  public RoutingDataSource dataSource() {
    RoutingDataSource dynamicMultipleDataSource = new RoutingDataSource();

    // 设置动态数据源
    Map<Object, Object> targetDataSources = new HashMap<>(2);
    targetDataSources.put(DataSourceConfig.DATASOURCE_DB1, dataSourceDb1);
    targetDataSources.put(DataSourceConfig.DATASOURCE_DB2, dataSourceDb2);
    dynamicMultipleDataSource.setTargetDataSources(targetDataSources);

    // 设置默认数据源
    Object defaultKey = targetDataSources.get(DynamicDataSourceConstant.DEFAULT_KEY);
    dynamicMultipleDataSource.setDefaultTargetDataSource(defaultKey);

    return dynamicMultipleDataSource;
  }
}
