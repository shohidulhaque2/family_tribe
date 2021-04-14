package com.shohidulhaque.user_registry_service.config;

import com.shohidulhaque.user_registry_service.dto_validation.PasswordSize;
import org.passay.CharacterCharacteristicsRule;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordValidator;
import org.passay.WhitespaceRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordValidatorConfiguration {

  @Bean
  PasswordValidator passwordValidator() {
    LengthRule r1 = new LengthRule(PasswordSize.MINIMAL_PASSWORD_LENGTH, PasswordSize.MAX_PASSWORD_LENGTH);
    CharacterCharacteristicsRule r2 = new CharacterCharacteristicsRule();
    // Define M (3 in this case)
    r2.setNumberOfCharacteristics(3);
    // Define elements of N (upper, lower, digit, symbol)
    r2.getRules().add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
    r2.getRules().add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
    r2.getRules().add(new CharacterRule(EnglishCharacterData.Digit, 1));
    r2.getRules().add(new CharacterRule(EnglishCharacterData.Special, 1));

    WhitespaceRule r3 = new WhitespaceRule();

    return new PasswordValidator(r1, r2, r3);
  }

}
