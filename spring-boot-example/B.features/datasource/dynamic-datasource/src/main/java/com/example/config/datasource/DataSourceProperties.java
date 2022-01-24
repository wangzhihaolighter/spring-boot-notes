package com.example.config.datasource;

import lombok.Data;

@Data
public class DataSourceProperties {
  private String driverClassName;
  private String jdbcUrl;
  private String username;
  private String password;
}
