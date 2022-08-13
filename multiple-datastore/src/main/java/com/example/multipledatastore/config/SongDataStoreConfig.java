package com.example.multipledatastore.config;

import com.example.multipledatastore.db2.Song;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "songEntityManagerFactory",
   transactionManagerRef = "songTransactionManager",
   basePackages = {"com.example.multipledatastore.db2"})
public class SongDataStoreConfig {
  @Bean(name = "songDataSourceProperties")
  @ConfigurationProperties(prefix = "db2.datasource")
  public DataSourceProperties memberDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "songDataSource")
  public DataSource dataSource(@Qualifier("songDataSourceProperties") DataSourceProperties firstDataSourceProperties) {
    return memberDataSourceProperties().initializeDataSourceBuilder()
       .type(HikariDataSource.class).build();
  }

  @Bean(name = "songEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean
  entityManagerFactory1(
     EntityManagerFactoryBuilder builder,
     @Qualifier("songDataSource") DataSource dataSource
  ) {
    return builder
       .dataSource(dataSource)
       .packages(Song.class)
       .build();
  }

  @Bean(name = "songTransactionManager")
  public PlatformTransactionManager transactionManager1(
     @Qualifier("songEntityManagerFactory") LocalContainerEntityManagerFactoryBean customerEntityManagerFactory
  ) {
    return new JpaTransactionManager(customerEntityManagerFactory.getObject());
  }
}
