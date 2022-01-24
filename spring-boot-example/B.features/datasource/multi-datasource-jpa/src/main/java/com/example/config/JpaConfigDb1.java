package com.example.config;

import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    // 配置dao所在目录
    basePackages = {"com.example.dao.db1"},
    // 配置事务管理器
    transactionManagerRef = JpaConfigDb1.TRANSACTION_MANAGER_REF,
    // 配置EntityManagerFactoryBean
    entityManagerFactoryRef = JpaConfigDb1.ENTITY_MANAGER_FACTORY_REF)
public class JpaConfigDb1 {

  public static final String ENTITY_MANAGER_FACTORY_REF = "entityManagerFactoryDb1";

  public static final String TRANSACTION_MANAGER_REF = "transactionManagerDb1";

  private final DataSource dataSource;

  private final JpaVendorAdapter jpaVendorAdapter;

  private final JpaProperties jpaProperties;

  private final HibernateProperties hibernateProperties;

  public JpaConfigDb1(
      @Qualifier(DataSourceConfig.DATASOURCE_DB1) DataSource dataSource,
      JpaVendorAdapter jpaVendorAdapter,
      JpaProperties jpaProperties,
      HibernateProperties hibernateProperties) {
    this.dataSource = dataSource;
    this.jpaVendorAdapter = jpaVendorAdapter;
    this.jpaProperties = jpaProperties;
    this.hibernateProperties = hibernateProperties;
  }

  @Primary
  @Bean(name = ENTITY_MANAGER_FACTORY_REF)
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factoryBean =
        new LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(dataSource);
    factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
    factoryBean.setJpaPropertyMap(getVendorProperties());
    factoryBean.setPackagesToScan("com.example.entity.db1");
    factoryBean.setPersistenceUnitName("db1PersistenceUnit");
    return factoryBean;
  }

  private Map<String, Object> getVendorProperties() {
    return hibernateProperties.determineHibernateProperties(
        jpaProperties.getProperties(), new HibernateSettings());
  }

  @Primary
  @Bean(name = "entityManagerDb1")
  public EntityManager entityManager() {
    return Objects.requireNonNull(entityManagerFactory().getObject()).createEntityManager();
  }

  @Primary
  @Bean(name = TRANSACTION_MANAGER_REF)
  public PlatformTransactionManager transactionManager() {
    return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory().getObject()));
  }
}
