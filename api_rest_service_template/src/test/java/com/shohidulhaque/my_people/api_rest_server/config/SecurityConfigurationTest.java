package com.shohidulhaque.my_people.discovery_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity
////hackish. this allows the test to set the web security first.
//@Order(1)
public class SecurityConfigurationTest  {

//  @Override
//  protected void configure( HttpSecurity http) throws Exception {
//    http.
//        httpBasic().disable()
//        .csrf().disable();
//  }

}
