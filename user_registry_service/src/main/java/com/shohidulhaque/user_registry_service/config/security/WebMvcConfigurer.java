package com.shohidulhaque.user_registry_service.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebMvcConfigurer implements
    org.springframework.web.servlet.config.annotation.WebMvcConfigurer {
  //https://spring.io/blog/2013/12/19/serving-static-web-content-with-spring-boot

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("*");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/static/**")
        .addResourceLocations("classpath:/static/static");
  }
}
