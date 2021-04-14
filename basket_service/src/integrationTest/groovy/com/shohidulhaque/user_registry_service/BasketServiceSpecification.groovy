package com.shohidulhaque.user_registry_service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@ContextConfiguration(initializers = Initializer.class)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = ["spring.cloud.discovery.enabled = false"])
class BasketServiceSpecification extends Specification {

    public static MySQLContainer staticSqlContainer
    public static Logger Logger = LoggerFactory.getLogger(BasketService.class)
    static {
        staticSqlContainer = new MySQLContainer()
                .withDatabaseName("foo")
                .withUsername("foo")
                .withPassword("secret")
        staticSqlContainer.start()
        Logger.info(staticSqlContainer.getJdbcUrl())
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + staticSqlContainer.getJdbcUrl(),
                    "spring.datasource.username=" + staticSqlContainer.getUsername(),
                    "spring.datasource.password=" + staticSqlContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment())
        }
    }

    @Shared
    public MySQLContainer sqlContainer = staticSqlContainer

    def setup() {
    }

}