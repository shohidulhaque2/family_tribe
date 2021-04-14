package com.shohidulhaque.user_registry_service.mapper;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.get.RegisteredPerson;
import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedUpdatePersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.model.PersonRegistration;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("unittest")
public class PersonRegistrationMapperTest {

    final static String EMAIL = "some_email@email.com";
    final static String FIRST_NAME = "some_first_name";
    final static String NICKNANE = "some_nickname";
    final static String LAST_NAME = "some_last_name";
    final static String PASSWORD = "some_password";
    final static String MATCHING_PASSWORD = "some_password";

    PersonRegistrationMapper personRegistrationMapper;

    PersonRegistration personRegistration;

    RegisteredPerson registeredPerson;


    @BeforeEach
    public void beforeEachTest(){
        this.personRegistrationMapper = PersonRegistrationMapper.INSTANCE;
        this.personRegistration = PersonRegistration
            .builder()
            .email(EMAIL)
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .nickname(NICKNANE)
            .build();

        this.registeredPerson =  RegisteredPerson
            .builder()
            .email(EMAIL)
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .nickname(NICKNANE)
            .build();

    }

    @Test
    public void givenAPersonRegistration_whenItIsMappedToARegisteredPerson_thenItIsMappedCorrectly(){
        RegisteredPerson registeredPerson = this.personRegistrationMapper.to(personRegistration);

        Assertions.assertThat(registeredPerson.getEmail()).isEqualTo(EMAIL);
        Assertions.assertThat(registeredPerson.getFirstName()).isEqualTo(FIRST_NAME);
        Assertions.assertThat(registeredPerson.getLastName()).isEqualTo(LAST_NAME);
        Assertions.assertThat(registeredPerson.getNickname()).isEqualTo(NICKNANE);
    }

//    @Test
//    public void givenAPersonRegistration_whenLastNameIsNullInTheValidatedUpdatePersonRegistrationDtoRequest_thenItIsOnlyCopied(){
//        ValidatedUpdatePersonRegistrationDtoRequest validatedUpdatePersonRegistrationDtoRequest = ValidatedUpdatePersonRegistrationDtoRequest
//            .builder()
//            .firstName(FIRST_NAME)
//            .lastName(null)
//            .build();
//
//        this.personRegistrationMapper.copyValueIfFeildIsNullInValidatedUpdatePersonRegistrationDtoRequest(personRegistration, validatedUpdatePersonRegistrationDtoRequest);
//        Assertions.assertThat(validatedUpdatePersonRegistrationDtoRequest.getLastName()).isEqualTo(LAST_NAME);
//    }

//    @Test
//    public void givenAPersonRegistration_whenFirstNameIsNullInTheValidatedUpdatePersonRegistrationDtoRequest_thenItIsOnlyCopied(){
//        ValidatedUpdatePersonRegistrationDtoRequest validatedUpdatePersonRegistrationDtoRequest = ValidatedUpdatePersonRegistrationDtoRequest
//            .builder()
//            .firstName(null)
//            .lastName(LAST_NAME)
//            .build();
//
//        this.personRegistrationMapper.copyValueIfFeildIsNullInValidatedUpdatePersonRegistrationDtoRequest(this.personRegistration, validatedUpdatePersonRegistrationDtoRequest);
//        Assertions.assertThat(validatedUpdatePersonRegistrationDtoRequest.getFirstName()).isEqualTo(FIRST_NAME);
//    }



//    @Test
//    public void givenAPersonRegistration_whenValuesAreNullInTheValidatedUpdatePersonRegistrationDtoRequest_thenOnlyValuesAreCopied(){
//        ValidatedUpdatePersonRegistrationDtoRequest validatedUpdatePersonRegistrationDtoRequest = ValidatedUpdatePersonRegistrationDtoRequest
//            .builder()
//            .firstName(null)
//            .lastName(null)
//            .build();
//
//        this.personRegistrationMapper.copyValueIfFeildIsNullInValidatedUpdatePersonRegistrationDtoRequest(this.personRegistration, validatedUpdatePersonRegistrationDtoRequest);
//        Assertions.assertThat(validatedUpdatePersonRegistrationDtoRequest.getFirstName()).isEqualTo(FIRST_NAME);
//        Assertions.assertThat(validatedUpdatePersonRegistrationDtoRequest.getLastName()).isEqualTo(LAST_NAME);
//    }

    @Test
    public void givenAPersonWantsToUpdateTheirFirstNameAndLastName_whenTheirDetailsAreUpdated_thenTheUpdateIsCorrect(){
        String IDENTITY_SERVER_USER_ID = "sdkjfsjkf";
        PersonRegistration personRegistration = PersonRegistration
            .builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .nickname(NICKNANE)
            .email(EMAIL)
            .uuid(UUID.randomUUID())
            .identityServerUserId(IDENTITY_SERVER_USER_ID)
            .build();

        String SOME_FIRST_NAME = "j98up;;";
        String SOME_LAST_NAME = "kjhykadd";

        ValidatedUpdatePersonRegistrationDtoRequest validatedUpdatePersonRegistrationDtoRequest = ValidatedUpdatePersonRegistrationDtoRequest
            .builder()
            .firstName(SOME_FIRST_NAME)
            .lastName(SOME_LAST_NAME)
            .build();

        this.personRegistrationMapper.to(validatedUpdatePersonRegistrationDtoRequest, personRegistration);

        Assertions.assertThat(personRegistration.getFirstName()).isEqualTo(SOME_FIRST_NAME);
        Assertions.assertThat(personRegistration.getLastName()).isEqualTo(SOME_LAST_NAME);

    }

}
