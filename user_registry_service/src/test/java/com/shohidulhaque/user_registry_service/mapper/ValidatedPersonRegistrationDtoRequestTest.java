package com.shohidulhaque.user_registry_service.mapper;

import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedPersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.model.PersonRegistration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
public class ValidatedPersonRegistrationDtoRequestTest {

    ValidatedPersonRegistrationDtoRequestMapper validatedPersonRegistrationDtoRequestMapper;

    final static String EMAIL = "some_email@email.com";
    final static String FIRST_NAME = "some_first_name";
    final static String NICKNANE = "some_nickname";
    final static String LAST_NAME = "some_last_name";
    final static String PASSWORD = "some_password";
    final static String MATCHING_PASSWORD = "some_password";

    @BeforeEach
    public void beforeEachTest(){
        this.validatedPersonRegistrationDtoRequestMapper = ValidatedPersonRegistrationDtoRequestMapper.INSTANCE;
    }

    @Test
    public void givenARegistrationDto_whenItIsMappedToAPersonRegistration_thenPersonRegistrationCorrectly(){

        ValidatedPersonRegistrationDtoRequest validatedPersonRegistrationDtoRequest = new ValidatedPersonRegistrationDtoRequest();
        validatedPersonRegistrationDtoRequest.setEmail(EMAIL);
        validatedPersonRegistrationDtoRequest.setFirstName(FIRST_NAME);
        validatedPersonRegistrationDtoRequest.setLastName(LAST_NAME);
        validatedPersonRegistrationDtoRequest.setNickname(NICKNANE);
        validatedPersonRegistrationDtoRequest.setPassword(PASSWORD);
        validatedPersonRegistrationDtoRequest.setMatchingPassword(MATCHING_PASSWORD);

        PersonRegistration personRegistration = this.validatedPersonRegistrationDtoRequestMapper
            .to(validatedPersonRegistrationDtoRequest);

        Assertions.assertThat(personRegistration.getEmail()).isEqualTo(EMAIL);
        Assertions.assertThat(personRegistration.getFirstName()).isEqualTo(FIRST_NAME);
        Assertions.assertThat(personRegistration.getLastName()).isEqualTo(LAST_NAME);
    }

}
