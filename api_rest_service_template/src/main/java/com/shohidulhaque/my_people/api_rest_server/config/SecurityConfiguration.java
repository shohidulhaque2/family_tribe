package com.shohidulhaque.my_people.discovery_service.config;

import com.shohidulhaque.my_people.discovery_service.rest_end_points.RestEndPointPaths;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  //http basic login
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .inMemoryAuthentication()
        .withUser("admin").password("{noop}password").roles("ADMIN")
        .and()
        .withUser("user").password("{noop}password").roles("USER");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/h2/**").permitAll()
        .antMatchers("/api/**").permitAll()
        .antMatchers(RestEndPointPaths.NullEndPoint).hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .cors().disable()
        .csrf().disable();

        http.headers().frameOptions().disable();
  }

}
