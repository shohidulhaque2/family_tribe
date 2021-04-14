package com.shohidulhaque.user_registry_service.config;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

public class PasswordValidatorConfigurationTest {

    PasswordValidator testObject;

    @BeforeEach
    public void beforeEachTest(){
        this.testObject = (new PasswordValidatorConfiguration()).passwordValidator();
    }


    @Test
    public void givenAWeakPassword_whenItIsCheckedForPasswordStrength_thenItIsStatedToBeWeak(){
        String password = "11111111";
        RuleResult result = this.testObject.validate(new PasswordData(password));
        Assertions.assertThat(result.isValid()).isFalse();
    }

    @Test
    public void givenAStrongPassword_whenItIsCheckedForPasswordStrength_thenItIsStatedToBeStrong(){
        String password = "Auy6+tfb'KAzd&)Q";
        RuleResult result = this.testObject.validate(new PasswordData(password));
        Assertions.assertThat(result.isValid()).isTrue();
    }

    @Test
    public void givenAPasswordIsLessThenEightCharacters_whenItIsCheckedItIsAStrongPassword_thenItIsStatedToBeUnacceptable(){
        String password = "*^y6+Tf";
        RuleResult result = this.testObject.validate(new PasswordData(password));
        Assertions.assertThat(result.isValid()).isFalse();
    }

    @Test
    public void givenAPasswordIsMoreThenThirtyTwoCharacters_whenItIsCheckedItIsAStrongPassword_thenItIsStatedToBeUnacceptable(){
        String password = "Auy6+tfb'KAzd&)QAuy6+tfb'KAzd&)QAuy6+tfb'KAzd&)QAuy6+tfb'KAzd&)QAuy6+tfb'KAzd&)QAuy6+tfb'KAzd&)Q";
        RuleResult result = this.testObject.validate(new PasswordData(password));
        Assertions.assertThat(result.isValid()).isFalse();
    }

    @Test
    public void givenAPasswordIsMoreThenThirtyTwoCharacters_whenItIsCheckedForViolations_thenViolationsMessagesAreReturned(){
        String password = "Auy6+tfb'KAzd&)QAuy6+tfb'KAzd&)QAuy6+tfb'KAzd&)QAuy6+tfb'KAzd&)QAuy6+tfb'KAzd&)QAuy6+tfb'KAzd&)Q";
        RuleResult result = this.testObject.validate(new PasswordData(password));
        Assertions.assertThat(result.isValid()).isFalse();
        List<String> violationMessages = this.testObject.getMessages(result);
        Assertions.assertThat(violationMessages).isNotEmpty();
    }

}
