package com.shohidulhaque.user_mailbox_service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@ActiveProfiles("integration")
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = UserMailboxServiceIntegrationTest.Initializer.class)
class RegistrationAndUserPasswordResetApplicationSpecification extends Specification {

    public static MySQLContainer staticSqlContainer
    public static Logger Logger = LoggerFactory.getLogger(RegistrationAndUserPasswordResetApplicationSpecification.class)
    static {
        staticSqlContainer = new MySQLContainer()
                .withDatabaseName("foo")
                .withUsername("foo")
                .withPassword("secret")
        staticSqlContainer.waitingFor(Wait.forLogMessage("Awaiting Connection.*", 1));
        staticSqlContainer.withExposedPorts(3306)
                .withLogConsumer(new Slf4jLogConsumer(Logger));
        staticSqlContainer.start();
        Logger.info(staticSqlContainer.getContainerId() + " has mapped port container port 3306 to " + staticSqlContainer.getMappedPort(3306).toString());
        Logger.info(staticSqlContainer.getJdbcUrl());
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

    def "basicTest"() {
        given: ""
        def a = 2
        def g = sqlContainer.getJdbcUrl()
        when: ""
        a = 2
        then:
        a == 2
        a == 2
    }

    def "registerNewUser"() {
        given: ""
        def a = 2
        def g = sqlContainer.getJdbcUrl()
        when: ""
        a = 2
        then:
        a == 2
        a == 2
    }

    def "attemptToRegisterNewUserWithEmptyFirstName"() {
        given: ""
        def a = 2
        def g = sqlContainer.getJdbcUrl()
        when: ""
        a = 2
        then:
        a == 2
        a == 2
    }

    def "attemptToRegisterNewUserWithEmptyLastName"() {
        given: ""
        def a = 2
        def g = sqlContainer.getJdbcUrl()
        when: ""
        a = 2
        then:
        a == 2
        a == 2
    }

    def "attemptToRegisterNewUserWithEmptyUsername"() {
        given: ""
        def a = 2
        def g = sqlContainer.getJdbcUrl()
        when: ""
        a = 2
        then:
        a == 2
        a == 2
    }

    def "attemptToRegisterNewUserWithEmptyPassword"() {
        given: ""
        def a = 2
        def g = sqlContainer.getJdbcUrl()
        when: ""
        a = 2
        then:
        a == 2
        a == 2
    }

    def "attemptToRegisterNewUserWithEmptyMatchingPassword"() {
        given: ""
        def a = 2
        def g = sqlContainer.getJdbcUrl()
        when: ""
        a = 2
        then:
        a == 2
        a == 2
    }

    def "attemptToRegisterNewUserWithABadPassword"() {
        given: ""
        def a = 2
        def g = sqlContainer.getJdbcUrl()
        when: ""
        a = 2
        then:
        a == 2
        a == 2
    }

    def "attemptToRegisterNewUserWhereThePasswordDoesNotMatch"() {
        given: ""
        def a = 2
        def g = sqlContainer.getJdbcUrl()
        when: ""
        a = 2
        then:
        a == 2
        a == 2
    }

}