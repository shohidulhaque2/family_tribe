package com.shohidulhaque.user_profile_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shohidulhaque.user_profile_service.UserMailboxServiceIntegrationTest.Initializer;
import java.util.UUID;
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
@SpringBootTest( properties = {"spring.cloud.discovery.enabled = false"})
@AutoConfigureMockMvc
@ContextConfiguration(initializers = Initializer.class)
public class UserMailboxServiceIntegrationTest {

    static UUID UUID_OF_EXISTING_MAILBOX = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcc");

    public static MySQLContainer staticSqlContainer;
    public static org.slf4j.Logger Logger = LoggerFactory.getLogger(UserMailboxServiceIntegrationTest.class);
    static {
        staticSqlContainer = new MySQLContainer()
            .withDatabaseName("user_mailbox_service")
            .withUsername("user")
            .withPassword("password");
        staticSqlContainer.waitingFor(Wait.forLogMessage("Awaiting Connection.*", 1));
        staticSqlContainer.withExposedPorts(3306)
        .withLogConsumer(new Slf4jLogConsumer(Logger));
        staticSqlContainer.start();
        Logger.debug(staticSqlContainer.getContainerId() + " has mapped port container port 3306 to " + staticSqlContainer.getMappedPort(3306).toString());
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

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    //==============================================================================================

    //==============================================================================================

    //==============================================================================================

    //==============================================================================================

    //==============================================================================================
}
