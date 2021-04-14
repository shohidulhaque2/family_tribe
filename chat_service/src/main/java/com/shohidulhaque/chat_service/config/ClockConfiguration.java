package com.shohidulhaque.chat_service.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClockConfiguration {
    @Bean
    Clock getClock(){
        return Clock.systemUTC();
    }
}
