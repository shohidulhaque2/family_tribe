package com.shohidulhaque.user_registry_service.config;


import com.shohidulhaque.user_registry_service.service.UuidGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UuidGeneratorConfiguration {

    @Bean
    public UuidGenerator getUuidGenerator(){
        return new UuidGenerator();
    }

}
