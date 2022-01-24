package com.example.config;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(
    // 配置dao所在目录
    basePackages = {"com.example.dao.db1"},
    // 配置事务管理器
    transactionManagerRef = "transactionManager",
    // 配置EntityManagerFactoryBean
    entityManagerFactoryRef = JpaConfigDb1.ENTITY_MANAGER_FACTORY_REF)
public class JpaConfigDb1 {

  public static final String ENTITY_MANAGER_FACTORY_REF = "entityManagerFactoryDb1";

  private final JpaVendorAdapter jpaVendorAdapter;

  private final JpaProperties jpaProperties;

  private final HibernateProperties hibernateProperties;

  private final DataSource dataSource;

  public JpaConfigDb1(
      JpaVendorAdapter jpaVendorAdapter,
      JpaProperties jpaProperties,
      HibernateProperties hibernateProperties,
      @Qualifier(DataSourceConfig.DATASOURCE_DB1) DataSource dataSource) {
    this.jpaVendorAdapter = jpaVendorAdapter;
    this.jpaProperties = jpaProperties;
    this.hibernateProperties = hibernateProperties;
    this.dataSource = dataSource;
  }

  @Primary
  @Bean(name = ENTITY_MANAGER_FACTORY_REF)
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManager =
        new LocalContainerEntityManagerFactoryBean();
    entityManager.setJtaDataSource(dataSource);
    entityManager.setJpaVendorAdapter(jpaVendorAdapter);
    entityManager.setPackagesToScan("com.example.entity.db1");
    entityManager.setPersistenceUnitName("db1PersistenceUnit");
    entityManager.setJpaPropertyMap(getVendorProperties());
    return entityManager;
  }

  private Map<String, Object> getVendorProperties() {
    return hibernateProperties.determineHibernateProperties(
        jpaProperties.getProperties(), new HibernateSettings());
  }
}
