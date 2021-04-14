package com.shohidulhaque.user_registry_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("integration")
@Testcontainers
@SpringBootTest(classes = BasketService.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = BasketServiceIntegrationTest.Initializer.class)
public class BasketServiceIntegrationTest {

    public static MySQLContainer staticSqlContainer;
    public static org.slf4j.Logger Logger = LoggerFactory.getLogger(
        BasketServiceIntegrationTest.class);

    static {
        staticSqlContainer = new MySQLContainer()
            .withDatabaseName("user_registration_service")
            .withUsername("user")
            .withPassword("password");
        staticSqlContainer.waitingFor(Wait.forLogMessage("Awaiting Connection.*", 1));
        staticSqlContainer.withExposedPorts(3306)
            .withLogConsumer(new Slf4jLogConsumer(Logger));
        staticSqlContainer.start();
        Logger.debug(
            staticSqlContainer.getContainerId() + " has mapped port container port 3306 to "
                + staticSqlContainer.getMappedPort(3306).toString());
        Logger.info(staticSqlContainer.getJdbcUrl());
    }

    static class Initializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=" + staticSqlContainer.getJdbcUrl(),
                "spring.datasource.username=" + staticSqlContainer.getUsername(),
                "spring.datasource.password=" + staticSqlContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    //================================================================================================
    @Autowired
    MockMvc mockMvc;
    //================================================================================================
    @Autowired
    ObjectMapper objectMapper;
    //================================================================================================
    volatile static int COUNT = 1;
    //=================================================================================================

}
