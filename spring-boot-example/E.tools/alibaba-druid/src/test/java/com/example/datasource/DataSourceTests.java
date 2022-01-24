package com.example.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class DataSourceTests {

  @Autowired DataSource dataSource;

  @SneakyThrows
  @Test
  public void druidDataSource() {
    log.info(dataSource.getClass().getName());

    if (dataSource instanceof DruidDataSource) {
      DruidDataSource druidDataSource = (DruidDataSource) dataSource;
      DruidPooledConnection connection = druidDataSource.getConnection();
      log.info("数据源连接池最大连接数：" + druidDataSource.getMaxActive());
      log.info("数据源连接池当前可用连接数：" + druidDataSource.getPoolingCount());
      log.info("数据源连接池当前活跃连接数：" + druidDataSource.getActiveCount());
    }
  }
}
