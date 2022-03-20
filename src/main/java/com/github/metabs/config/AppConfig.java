package com.github.metabs.config;

import com.github.metabs.model.dto.SaveElementDto;
import com.github.metabs.model.dto.validator.RequestElementValidator;
import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class AppConfig {

  @Bean
  public Neo4jTransactionManager transactionManager(Driver driver) {
    return new Neo4jTransactionManager(driver);
  }

  @Bean
  @RequestScope
  public RequestElementValidator requestElementValidator() {
    return new RequestElementValidator(new SaveElementDto());
  }

}

