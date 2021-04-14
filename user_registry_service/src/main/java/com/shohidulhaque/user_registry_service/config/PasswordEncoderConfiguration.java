package com.shohidulhaque.user_registry_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration {

    //TODO: cannot get this work. The spring lib and OK use different rounds. find a library where rounds match
    //OKTA - USE A 10 ROUNDS
    final static int LOG_ROUNDS = 10;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(LOG_ROUNDS);

    }
}
