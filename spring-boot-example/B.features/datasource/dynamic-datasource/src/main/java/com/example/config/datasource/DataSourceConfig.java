package com.example.config.datasource;

import com.mysql.cj.jdbc.MysqlXADataSource;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

  // ==================== db1 ====================

  public static final String DATASOURCE_DB1 = "dataSourceDb1";

  @Bean("db1DataSourceProperties")
  @ConfigurationProperties(prefix = "spring.datasource.db1")
  public DataSourceProperties db1DataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = DATASOURCE_DB1)
  public DataSource dataSourceDb1() {
    AtomikosDataSourceBean atomikosDataSourceBean =
        getAtomikosDataSourceBean(db1DataSourceProperties());
    atomikosDataSourceBean.setUniqueResourceName("db1");
    return atomikosDataSourceBean;
  }

  // ==================== db2 ====================

  public static final String DATASOURCE_DB2 = "dataSourceDb2";

  @Bean(name = "db2DataSourceProperties")
  @ConfigurationProperties(prefix = "spring.datasource.db2")
  public DataSourceProperties db2DataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = DATASOURCE_DB2)
  public DataSource dataSourceDb2() {
    AtomikosDataSourceBean atomikosDataSourceBean =
        getAtomikosDataSourceBean(db2DataSourceProperties());
    atomikosDataSourceBean.setUniqueResourceName("db2");
    return atomikosDataSourceBean;
  }

  // ==================== atomikos ====================

  @Bean
  @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource")
  public com.atomikos.jdbc.AtomikosDataSourceBean atomikosDataSourceBean() {
    return new com.atomikos.jdbc.AtomikosDataSourceBean();
  }

  @SneakyThrows
  private AtomikosDataSourceBean getAtomikosDataSourceBean(
      DataSourceProperties dataSourceProperties) {
    // XADataSource
    MysqlXADataSource xaDataSource = new MysqlXADataSource();
    xaDataSource.setPinGlobalTxToPhysicalConnection(true);
    xaDataSource.setUrl(dataSourceProperties.getJdbcUrl());
    xaDataSource.setUser(dataSourceProperties.getUsername());
    xaDataSource.setPassword(dataSourceProperties.getPassword());

    // atomikos
    AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
    BeanUtils.copyProperties(atomikosDataSourceBean(), atomikosDataSourceBean);
    atomikosDataSourceBean.setXaDataSource(xaDataSource);

    return atomikosDataSourceBean;
  }
}
