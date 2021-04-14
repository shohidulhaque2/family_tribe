package com.shohidulhaque.my_people.discovery_service.config.data;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@AllArgsConstructor
//@NoArgsConstructor
public class LiquibaseConfig {

//  @Autowired
//  DataSource dataSource;
//
//  @Bean
//  public SpringLiquibase liquibase() {
//    SpringLiquibase liquibase = new SpringLiquibase();
//    liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
//    liquibase.setDataSource(dataSource);
//    return liquibase;
//  }


}
