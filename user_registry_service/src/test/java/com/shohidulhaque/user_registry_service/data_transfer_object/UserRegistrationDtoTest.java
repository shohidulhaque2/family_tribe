package com.shohidulhaque.user_registry_service.data_transfer_object;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;

public class UserRegistrationDtoTest {

    final static String EMAIL = "some_email@email.com";
    final static String FIRST_NAMNE = "some_first_name";
    final static String LAST_NAME = "some_last_name";
    final static String NICKNAME = "some_nickname";
    final static String PASSWORD = "some_password";
    final static String MATCHING_PASSWORD = "some_password";

    @Disabled
    public void givenThereIsAPassword_whenItIsCleared_thenItIsRemoved(){
        ValidatedPersonRegistrationDtoRequest validatedPersonRegistrationDtoRequest = new ValidatedPersonRegistrationDtoRequest();
        validatedPersonRegistrationDtoRequest.setEmail(EMAIL);
        validatedPersonRegistrationDtoRequest.setFirstName(FIRST_NAMNE);
        validatedPersonRegistrationDtoRequest.setLastName(LAST_NAME);
        validatedPersonRegistrationDtoRequest.setNickname(NICKNAME);
        validatedPersonRegistrationDtoRequest.setPassword(PASSWORD);
        validatedPersonRegistrationDtoRequest.setMatchingPassword(MATCHING_PASSWORD);
//        validatedPersonRegistrationDtoRequest.clearSensitiveData();
        Assertions.assertThat(validatedPersonRegistrationDtoRequest.getPassword()).isEqualTo(
            ValidatedPersonRegistrationDtoRequest.EMPTY_PASSWORD_VALUE);
        Assertions.assertThat(validatedPersonRegistrationDtoRequest.getMatchingPassword()).isEqualTo(
            ValidatedPersonRegistrationDtoRequest.EMPTY_PASSWORD_VALUE);
        Assertions.assertThat(validatedPersonRegistrationDtoRequest.getFirstName()).isNotBlank();
        Assertions.assertThat(validatedPersonRegistrationDtoRequest.getLastName()).isNotBlank();
        Assertions.assertThat(validatedPersonRegistrationDtoRequest.getNickname()).isNotBlank();
    }
}
