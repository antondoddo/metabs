package com.github.metabs.config;

import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;

@Configuration
public class AppConfig {

  @Bean("neo4jTransactionManager")
  public Neo4jTransactionManager transactionManager(Driver driver) {
    return new Neo4jTransactionManager(driver);
  }
}
