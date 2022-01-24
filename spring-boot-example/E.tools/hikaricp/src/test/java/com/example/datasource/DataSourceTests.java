package com.example.datasource;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import java.sql.Connection;
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
  public void hikariDataSource() {
    log.info(dataSource.getClass().getName());

    if (dataSource instanceof HikariDataSource) {
      HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
      Connection connection = hikariDataSource.getConnection();
      String poolName = hikariDataSource.getPoolName();
      HikariPoolMXBean mx = hikariDataSource.getHikariPoolMXBean();

      log.info(
          "{} - stats (total={}, active={}, idle={}, waiting={})",
          poolName,
          mx.getTotalConnections(),
          mx.getActiveConnections(),
          mx.getIdleConnections(),
          mx.getThreadsAwaitingConnection());
    }
  }
}
