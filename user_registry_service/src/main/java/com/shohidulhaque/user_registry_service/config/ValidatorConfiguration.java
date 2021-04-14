package com.shohidulhaque.user_registry_service.config;

import com.shohidulhaque.user_registry_service.dto_validation.EmailShouldNotExistValidator;
import com.shohidulhaque.user_registry_service.dto_validation.NotWeakPasswordValidator;
import com.shohidulhaque.user_registry_service.dto_validation.PasswordMatchesValidator;
import com.shohidulhaque.user_registry_service.dto_validation.UsernameShouldNotExistValidator;
import com.shohidulhaque.user_registry_service.repository.PersonRegistrationRepository;
import org.passay.PasswordValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfiguration {

    @Bean
    public EmailShouldNotExistValidator getEmailShouldNotExistValidator(
        PersonRegistrationRepository personRegistrationRepository){
        return new EmailShouldNotExistValidator(personRegistrationRepository);
    }

    @Bean
    public NotWeakPasswordValidator getNotWeakPasswordValidator(PasswordValidator passwordValidator){
        return new NotWeakPasswordValidator(passwordValidator);
    }

    @Bean
    public PasswordMatchesValidator getPasswordMatchesValidator(){
        return new PasswordMatchesValidator();
    }

    @Bean
    public UsernameShouldNotExistValidator getUsernameShouldNotExistValidator(PersonRegistrationRepository personRegistrationRepository){
        return new UsernameShouldNotExistValidator(personRegistrationRepository);
    }

}
