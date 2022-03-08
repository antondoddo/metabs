package com.github.metabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableNeo4jRepositories
@EnableTransactionManagement
public class MetabsApplication {

  public static void main(String[] args) {
    SpringApplication.run(MetabsApplication.class, args);
  }

}
