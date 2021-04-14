package com.shohidulhaque.user_registry_service.config;

import com.shohidulhaque.user_registry_service.properties.EmailVerificationProperties;
import com.shohidulhaque.user_registry_service.service.user_invitation.MailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MailSenderConfiguration {

    @Bean
    public MailSender getMailSender(Environment environment,
        EmailVerificationProperties emailVerificationProperties){
        return new MailSender(environment, emailVerificationProperties);
    }

}
