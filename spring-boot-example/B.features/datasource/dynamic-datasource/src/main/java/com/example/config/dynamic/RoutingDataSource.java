package com.example.config.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

  private String key = DynamicDataSourceConstant.DEFAULT_KEY;

  public void change(String dataSourceKey) {
    this.key = dataSourceKey;
  }

  /** 重置为默认数据源key */
  private void reset() {
    this.key = DynamicDataSourceConstant.DEFAULT_KEY;
  }

  @Override
  protected Object determineCurrentLookupKey() {
    // 当前动态数据源指定的数据源key
    String dataSourceKey = key;
    log.info("----- dynamic datasource key: {}-----", dataSourceKey);

    // 重置
    reset();

    return dataSourceKey;
  }
}
