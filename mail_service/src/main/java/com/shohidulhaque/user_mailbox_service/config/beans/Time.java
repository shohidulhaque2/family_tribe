package com.shohidulhaque.user_mailbox_service.config.beans;


import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Time {

    @Bean
    public Clock getClock(){
        return Clock.systemUTC();
    }

}
