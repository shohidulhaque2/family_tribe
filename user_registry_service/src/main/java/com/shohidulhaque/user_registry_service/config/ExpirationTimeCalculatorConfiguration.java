package com.shohidulhaque.user_registry_service.config;

import com.shohidulhaque.user_registry_service.service.ExpirationTimeCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpirationTimeCalculatorConfiguration {

    @Bean
    public ExpirationTimeCalculator getExpirationTimeCalculator(){
        return new ExpirationTimeCalculator();
    }
}
