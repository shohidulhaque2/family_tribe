package com.shohidulhaque.profile_service
//import org.testcontainers.containers.PostgreSQLContainer

import spock.lang.Specification

//@Testcontainers
class PostgresContainerIT extends Specification {
//
//    @Shared
//    PostgreSQLContainer sqlContainer = new PostgreSQLContainer("postgres:10.7")
//            .withDatabaseName("foo")
//            .withUsername("foo")
//            .withPassword("secret")
//
//    class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//        void initialize(ConfigurableApplicationContext configurableApplicationContext) {
//            TestPropertyValues.of(
//                    "spring.datasource.url=" + sqlContainer.getJdbcUrl(),
//                    "spring.datasource.username=" + sqlContainer.getUsername(),
//                    "spring.datasource.password=" + sqlContainer.getPassword()
//            ).applyTo(configurableApplicationContext.getEnvironment())
//        }
//    }
//
//    def setup(){
//        sqlContainer.start()
//    }
//
//    def "waits until postgres accepts jdbc connections"() {
//
//        given: "a jdbc connection"
//        HikariConfig hikariConfig = new HikariConfig()
//        hikariConfig.setJdbcUrl(sqlContainer.jdbcUrl)
//        hikariConfig.setUsername("foo")
//        hikariConfig.setPassword("secret")
//        HikariDataSource ds = new HikariDataSource(hikariConfig)
//
//        when: "querying the database"
//        Statement statement = ds.getConnection().createStatement()
//        statement.execute("SELECT 1")
//        ResultSet resultSet = statement.getResultSet()
//        resultSet.next()
//
//        then: "result is returned"
//        int resultSetInt = resultSet.getInt(1)
//        resultSetInt == 1
//
//        cleanup:
//        ds.close()
//    }

}