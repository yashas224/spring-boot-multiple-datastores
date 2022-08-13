package com.example.multipledatastore.config;

import com.example.multipledatastore.db1.Book;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "bookEntityManagerFactory",
   transactionManagerRef = "bookTransactionManager",
   basePackages = {"com.example.multipledatastore.db1"})
public class BookDataStoreConfig {

  @Primary
  @Bean(name = "bookDataSourceProperties")
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSourceProperties memberDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean(name = "bookDataSource")
  public DataSource dataSource(@Qualifier("bookDataSourceProperties") DataSourceProperties firstDataSourceProperties) {
    return memberDataSourceProperties().initializeDataSourceBuilder()
       .type(HikariDataSource.class).build();
  }

  @Primary
  @Bean(name = "bookEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean
  entityManagerFactory1(
     EntityManagerFactoryBuilder builder,
     @Qualifier("bookDataSource") DataSource dataSource
  ) {
    return builder
       .dataSource(dataSource)
       .packages(Book.class)
       .build();
  }

  @Primary
  @Bean(name = "bookTransactionManager")
  public PlatformTransactionManager transactionManager1(
     @Qualifier("bookEntityManagerFactory") LocalContainerEntityManagerFactoryBean customerEntityManagerFactory
  ) {
    return new JpaTransactionManager(customerEntityManagerFactory.getObject());
  }
}
