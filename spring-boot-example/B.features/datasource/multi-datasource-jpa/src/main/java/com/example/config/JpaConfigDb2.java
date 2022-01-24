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
    basePackages = {"com.example.dao.db2"},
    // 配置事务管理器
    transactionManagerRef = JpaConfigDb2.TRANSACTION_MANAGER_REF,
    // 配置EntityManagerFactoryBean
    entityManagerFactoryRef = JpaConfigDb2.ENTITY_MANAGER_FACTORY_REF)
public class JpaConfigDb2 {

  public static final String ENTITY_MANAGER_FACTORY_REF = "entityManagerFactoryDb2";

  public static final String TRANSACTION_MANAGER_REF = "transactionManagerDb2";

  private final JpaVendorAdapter jpaVendorAdapter;

  private final JpaProperties jpaProperties;

  private final HibernateProperties hibernateProperties;

  private final DataSource dataSource;

  public JpaConfigDb2(
      JpaVendorAdapter jpaVendorAdapter,
      JpaProperties jpaProperties,
      HibernateProperties hibernateProperties,
      @Qualifier(DataSourceConfig.DATASOURCE_DB2) DataSource dataSource) {
    this.jpaVendorAdapter = jpaVendorAdapter;
    this.jpaProperties = jpaProperties;
    this.hibernateProperties = hibernateProperties;
    this.dataSource = dataSource;
  }

  @Bean(name = ENTITY_MANAGER_FACTORY_REF)
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManager =
        new LocalContainerEntityManagerFactoryBean();
    entityManager.setDataSource(dataSource);
    entityManager.setJpaVendorAdapter(jpaVendorAdapter);
    entityManager.setJpaPropertyMap(getVendorProperties());
    entityManager.setPackagesToScan("com.example.entity.db2");
    entityManager.setPersistenceUnitName("db2PersistenceUnit");
    return entityManager;
  }

  private Map<String, Object> getVendorProperties() {
    return hibernateProperties.determineHibernateProperties(
        jpaProperties.getProperties(), new HibernateSettings());
  }

  @Bean(name = "entityManagerDb2")
  public EntityManager entityManager() {
    return Objects.requireNonNull(entityManagerFactory().getObject()).createEntityManager();
  }

  @Bean(name = TRANSACTION_MANAGER_REF)
  public PlatformTransactionManager transactionManager() {
    return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory().getObject()));
  }
}
