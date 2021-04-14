package com.shohidulhaque.api_rest_service;


import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
//https://ajayiyengar.com/2020/05/18/integration-testing-using-testcontainers-in-a-spring-boot-2-jpa-application/


public class DockerPostresContainerTest {
  private static PostgreSQLContainer sqlContainer;

  static {
    sqlContainer = new PostgreSQLContainer("postgres:10.7")
        .withDatabaseName("integration-tests-db")
        .withUsername("sa")
        .withPassword("sa");
    sqlContainer.start();
  }

  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues.of(
          "spring.datasource.url=" + sqlContainer.getJdbcUrl(),
          "spring.datasource.username=" + sqlContainer.getUsername(),
          "spring.datasource.password=" + sqlContainer.getPassword()
      ).applyTo(configurableApplicationContext.getEnvironment());
    }
  }

}


