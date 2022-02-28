package com.github.metabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MetabsApplication {

  public static void main(String[] args) {
    SpringApplication.run(MetabsApplication.class, args);
  }

}
