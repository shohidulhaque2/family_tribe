package com.shohidulhaque.user_registry_service.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HashFunctionConfiguration {

  @Bean
  public MessageDigest sha256messageDigest() throws NoSuchAlgorithmException {
    return MessageDigest.getInstance("SHA-256");
  }
}
