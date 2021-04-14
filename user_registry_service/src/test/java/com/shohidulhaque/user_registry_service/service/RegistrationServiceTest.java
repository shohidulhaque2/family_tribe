package com.shohidulhaque.user_registry_service.service;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.User;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.ContentPersonRegistrationErrorResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.ContentPersonRegistrationSuccessResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.PersonRegistrationDtoResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.get.RegisteredPerson;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.FieldErrorResponse;
import com.shohidulhaque.user_registry_service.config.ExpirationTimeCalculatorConfiguration;
import com.shohidulhaque.user_registry_service.config.MessageSourceConfiguration;
import com.shohidulhaque.user_registry_service.config.PasswordEncoderConfiguration;
import com.shohidulhaque.user_registry_service.config.PasswordValidatorConfiguration;
import com.shohidulhaque.user_registry_service.config.UuidGeneratorConfiguration;
import com.shohidulhaque.user_registry_service.config.ValidatorConfiguration;
import com.shohidulhaque.user_registry_service.config.authorization_server.openid_connect_1.okta.OktaConstants;
import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedPersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedUpdatePersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.mapper.PersonRegistrationMapper;
import com.shohidulhaque.user_registry_service.mapper.ValidatedPersonRegistrationDtoRequestMapper;
import com.shohidulhaque.user_registry_service.properties.AccountActivationProperties;
import com.shohidulhaque.user_registry_service.repository.PersonRegistrationRepository;
import com.shohidulhaque.user_registry_service.repository.PersonVerificationTokenRepository;
import com.shohidulhaque.user_registry_service.service.registration.RegistrationService;
import com.shohidulhaque.user_registry_service.service.registration.listener.OpenIdConnect2Proxy;
import com.shohidulhaque.user_registry_service.service.user_invitation.MailSender;
import java.util.Map;
import java.util.UUID;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.passay.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ActiveProfiles("development")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
    ValidatorConfiguration.class,
    PasswordValidatorConfiguration.class,
    UuidGeneratorConfiguration.class,
    ExpirationTimeCalculatorConfiguration.class,
    MessageSourceConfiguration.class,
    PasswordEncoderConfiguration.class,
    AccountActivationProperties.class
    })
public class RegistrationServiceTest {

    //==============================================================================================
    static final UUID UUID_OF_EXISTING_PERSON = UUID.fromString("ee3c41a0-1d7a-4082-816a-5143884bd37d");
    static final String EXISTING_NICKNAME = "nickname";
    static final String EXISTING_EMAIL_ADDRESS = "email@email.com";
    //==============================================================================================
    static final UUID UUID_OF_NON_EXISTING_PERSON = UUID.fromString("7ba66e96-392e-425a-8e54-d51246b9a90d");
    static final String NON_EXISTING_EMAIL_ADDRESS = "project@email.com";
    static final String FIRST_NAME = "ah!";
    static final String LAST_NAME = "no";
    static final String NON_EXISTING_NICKNAME = "i_am_so_tired_of_this_project";
    static final String PASSWORD = "Auy6+tfb'KAzd&)Q";
    static final String PASSWORD_THAT_IS_TO_SHORT = "KAzd&)Q";
    static final String INCORRECT_MATCHING_PASSWORD = "Q)&dzAK'bft+6yuA";
    static final String VALIDATION_LINK = "https://keycloak.org/bfda190e-acf9-477c-9a5d-c240e1f04558";
    static final String USER_ID_FROM_OAUTH2_SERVER = "4fcd354c-bd0d-432c-857d-61eac0d69421";
    static final String WEAK_PASSWORD = "12345678910";
    static final String STRONG_PASSWORD = "Q)&dzAK'bft+6yuA";
    static final String MISMATCHED_PASSWORD = "Q)&dzAK'bft+6yuAQ)&dzAK'bft+6yuA";
    //==============================================================================================
    @org.springframework.boot.test.context.TestConfiguration
    public static class TestConfiguration{
        @Bean
        public Validator getValidator(LocalValidatorFactoryBean localValidatorFactoryBean){
            return localValidatorFactoryBean.getValidator();
        }
    }
    //==============================================================================================
    @MockBean
    OpenIdConnect2Proxy openIdConnect2Proxy;
    //==============================================================================================
    @Autowired
    UuidGenerator uuidGenerator;
    //==============================================================================================
    @Autowired
    PasswordValidator passwordValidator;

    @Autowired
    Validator validator;
    //==============================================================================================
    @Autowired
    PersonRegistrationRepository userRegistrationRepository;

    @Autowired
    PersonRegistrationRepository personRegistrationRepository;

    @Autowired
    PersonVerificationTokenRepository personVerificationTokenRepository;
    //==============================================================================================
    ValidatedPersonRegistrationDtoRequestMapper validatedPersonRegistrationDtoRequestMapper;

    PersonRegistrationMapper personRegistrationMapper;
    //==============================================================================================
    @Autowired
    ApplicationEventPublisher eventPublisher;
    //==============================================================================================
    @Autowired
    ExpirationTimeCalculator expirationTimeCalculator;
    //==============================================================================================
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    //==============================================================================================
    @Autowired
    AccountActivationProperties accountActivationProperties;
    //==============================================================================================
    @MockBean
    MailSender mailSender;
    //==============================================================================================
    @MockBean
    Client oktaClient;
    //==============================================================================================
    RegistrationService testObject;
    @BeforeEach
    public void beforeEachTest(){
        this.validatedPersonRegistrationDtoRequestMapper = ValidatedPersonRegistrationDtoRequestMapper.INSTANCE;
        this.personRegistrationMapper = PersonRegistrationMapper.INSTANCE;

        this.testObject = new RegistrationService(
            this.personRegistrationRepository,
            this.validatedPersonRegistrationDtoRequestMapper,
            this.personRegistrationMapper,
            this.passwordValidator,
            this.validator,
            this.uuidGenerator,
            this.mailSender,
            this.eventPublisher,
            this.expirationTimeCalculator,
            this.personVerificationTokenRepository,
            this.bCryptPasswordEncoder,
            this.accountActivationProperties,
            this.openIdConnect2Proxy
        );
    }


    @Test
    public void givenAnExistingPerson_whenTheyAreRetrievedUsingTheirUuid_thenTheyAreReturned(){
        PersonRegistrationDtoResponse personRegistrationDtoResponse = this.testObject.getPerson(UUID_OF_EXISTING_PERSON);
        ContentPersonRegistrationSuccessResponse success = personRegistrationDtoResponse.getContent().getSuccess()[0];
        RegisteredPerson registeredPerson = success.getPayload();
        Assertions.assertThat(registeredPerson.getUuid()).isEqualTo(UUID_OF_EXISTING_PERSON);
    }

    @Test
    public void givenAnANonExistingPerson_whenTheyAreRetrievedUsingTheirUuid_thenTheyAreNotReturned(){
        PersonRegistrationDtoResponse personRegistrationDtoResponse = this.testObject.getPerson(UUID_OF_NON_EXISTING_PERSON);
        Assertions.assertThat(personRegistrationDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        ContentPersonRegistrationErrorResponse contentPersonRegistrationErrorResponse = personRegistrationDtoResponse
            .getContent().getError()[0];
        ErrorType type = contentPersonRegistrationErrorResponse.getType();
        Assertions.assertThat(type.getMessage()).isEqualTo("Error when performing registration operation");
    }

    @Test
    public void givenANewPersonWantsToRegister_whenTheyRegister_thenTheirRegistrationIsSuccessfulAndAnActivationEmailIsSentToThem(){

        User userMock = Mockito.mock(User.class);
        Mockito.when(userMock.getId()).thenReturn(USER_ID_FROM_OAUTH2_SERVER);
        Map mockMap = Map.of(OktaConstants.ACTIVATE_LINK, Map.of(OktaConstants.ACTIVE_LINK_HREF, VALIDATION_LINK));
        Mockito.when(userMock.getLinks()).thenReturn(mockMap);
        Mockito.when(openIdConnect2Proxy
                        .registerUserWithOAuthServer(
                            Mockito.any(ValidatedPersonRegistrationDtoRequest.class),
                            Mockito.anyString(),
                            Mockito.anySet(),
                            Mockito.any(UUID.class)))
                    .thenReturn(userMock);

        ValidatedPersonRegistrationDtoRequest validatedPersonRegistrationDtoRequest = new ValidatedPersonRegistrationDtoRequest();
        validatedPersonRegistrationDtoRequest.setEmail(NON_EXISTING_EMAIL_ADDRESS);
        validatedPersonRegistrationDtoRequest.setFirstName(FIRST_NAME);
        validatedPersonRegistrationDtoRequest.setLastName(LAST_NAME);
        validatedPersonRegistrationDtoRequest.setNickname(NON_EXISTING_NICKNAME);
        validatedPersonRegistrationDtoRequest.setPassword(PASSWORD);
        validatedPersonRegistrationDtoRequest.setMatchingPassword(PASSWORD);

        PersonRegistrationDtoResponse personRegistrationDtoResponse = this.testObject.registerPerson(validatedPersonRegistrationDtoRequest);
        Assertions.assertThat(personRegistrationDtoResponse.getContent().getResponseType().getCode()).isEqualTo(
            HttpStatus.CREATED.value());
        Assertions.assertThat(personRegistrationDtoResponse.getContent().getResponseType().getMessage()).isEqualTo(
            HttpStatus.CREATED.toString());
        Assertions.assertThat(personRegistrationDtoResponse.getContent().getSuccess()).hasSize(1);
        ContentPersonRegistrationSuccessResponse success = personRegistrationDtoResponse
            .getContent().getSuccess()[0];
        RegisteredPerson registeredPerson = success.getPayload();
        UUID.fromString(registeredPerson.getUuid().toString());

        Assertions.assertThat(registeredPerson.getFirstName()).isEqualTo(FIRST_NAME);
        Assertions.assertThat(registeredPerson.getLastName()).isEqualTo(LAST_NAME);
        Assertions.assertThat(registeredPerson.getEmail()).isEqualTo(NON_EXISTING_EMAIL_ADDRESS);
        Assertions.assertThat(registeredPerson.getNickname()).isEqualTo(NON_EXISTING_NICKNAME);

        Mockito.verify(
            openIdConnect2Proxy, Mockito.times(1)).
            registerUserWithOAuthServer(
                Mockito.any(ValidatedPersonRegistrationDtoRequest.class),
                Mockito.anyString(),
                Mockito.anySet(),
                Mockito.any(UUID.class));
        Mockito.verify(userMock, Mockito.times(1)).getLinks();
        Mockito.verify(userMock, Mockito.times(1)).getId();

    }

    @Test
    public void givenAnPersonWantsToRegisterWithTheAExistingEmail_whenTheyRegister_thenTheirRegistrationFails(){
        ValidatedPersonRegistrationDtoRequest validatedPersonRegistrationDtoRequest = new ValidatedPersonRegistrationDtoRequest();
        validatedPersonRegistrationDtoRequest.setEmail(EXISTING_EMAIL_ADDRESS);
        validatedPersonRegistrationDtoRequest.setFirstName(FIRST_NAME);
        validatedPersonRegistrationDtoRequest.setLastName(LAST_NAME);
        validatedPersonRegistrationDtoRequest.setNickname(NON_EXISTING_NICKNAME);
        validatedPersonRegistrationDtoRequest.setPassword(PASSWORD);
        validatedPersonRegistrationDtoRequest.setMatchingPassword(PASSWORD);

        PersonRegistrationDtoResponse personRegistrationDtoResponse = this.testObject.registerPerson(validatedPersonRegistrationDtoRequest);
        ContentPersonRegistrationErrorResponse[] error = personRegistrationDtoResponse.getContent().getError();
        Assertions.assertThat(error).hasSize(1);

        final Condition<FieldErrorResponse[]> listIsGreaterThanOne = new Condition<>(x -> x.length > 0, "array must be greater than 1.");
        ContentPersonRegistrationErrorResponse contentPersonRegistrationErrorResponse = error[0];
        FieldErrorResponse[] payload = contentPersonRegistrationErrorResponse.getPayload();
        Assertions.assertThat(payload).is(listIsGreaterThanOne);
    }

    @Test
    public void givenPersonWantsToRegisterWithTheAnExistingNickname_whenTheyRegister_thenTheirRegistrationFails(){
        ValidatedPersonRegistrationDtoRequest validatedPersonRegistrationDtoRequest = new ValidatedPersonRegistrationDtoRequest();
        validatedPersonRegistrationDtoRequest.setEmail(NON_EXISTING_EMAIL_ADDRESS);
        validatedPersonRegistrationDtoRequest.setFirstName(FIRST_NAME);
        validatedPersonRegistrationDtoRequest.setLastName(LAST_NAME);
        validatedPersonRegistrationDtoRequest.setNickname(EXISTING_NICKNAME);
        validatedPersonRegistrationDtoRequest.setPassword(PASSWORD);
        validatedPersonRegistrationDtoRequest.setMatchingPassword(PASSWORD);

        PersonRegistrationDtoResponse personRegistrationDtoResponse = this.testObject.registerPerson(validatedPersonRegistrationDtoRequest);
        ContentPersonRegistrationErrorResponse[] error = personRegistrationDtoResponse.getContent().getError();
        Assertions.assertThat(error).hasSize(1);

        final Condition<FieldErrorResponse[]> listIsGreaterThanOne = new Condition<>(x -> x.length > 0, "array must be greater than 1.");
        ContentPersonRegistrationErrorResponse contentPersonRegistrationErrorResponse = error[0];
        FieldErrorResponse[] payload = contentPersonRegistrationErrorResponse.getPayload();
        Assertions.assertThat(payload).is(listIsGreaterThanOne);
    }

    @Test
    public void givenAPersonWantsToRegisterWithMismatchedPasswords_whenTheyRegister_thenTheirRegistrationFails(){
        ValidatedPersonRegistrationDtoRequest validatedPersonRegistrationDtoRequest = new ValidatedPersonRegistrationDtoRequest();
        validatedPersonRegistrationDtoRequest.setEmail(NON_EXISTING_EMAIL_ADDRESS);
        validatedPersonRegistrationDtoRequest.setFirstName(FIRST_NAME);
        validatedPersonRegistrationDtoRequest.setLastName(LAST_NAME);
        validatedPersonRegistrationDtoRequest.setNickname(NON_EXISTING_NICKNAME);
        validatedPersonRegistrationDtoRequest.setPassword(STRONG_PASSWORD);
        validatedPersonRegistrationDtoRequest.setMatchingPassword(MISMATCHED_PASSWORD);

        PersonRegistrationDtoResponse personRegistrationDtoResponse = this.testObject.registerPerson(validatedPersonRegistrationDtoRequest);
        ContentPersonRegistrationErrorResponse[] error = personRegistrationDtoResponse.getContent().getError();
        Assertions.assertThat(error).hasSize(1);

        final Condition<FieldErrorResponse[]> listIsGreaterThanOne = new Condition<>(x -> x.length > 0, "array must be greater than 1.");
        ContentPersonRegistrationErrorResponse contentPersonRegistrationErrorResponse = error[0];
        FieldErrorResponse[] payload = contentPersonRegistrationErrorResponse.getPayload();
        Assertions.assertThat(payload).is(listIsGreaterThanOne);
    }

    @Test
    public void givenAPersonWantsToRegisterWithAWeakPassword_whenTheyRegister_thenTheirRegistrationFails(){
        ValidatedPersonRegistrationDtoRequest validatedPersonRegistrationDtoRequest = new ValidatedPersonRegistrationDtoRequest();
        validatedPersonRegistrationDtoRequest.setEmail(NON_EXISTING_EMAIL_ADDRESS);
        validatedPersonRegistrationDtoRequest.setFirstName(FIRST_NAME);
        validatedPersonRegistrationDtoRequest.setLastName(LAST_NAME);
        validatedPersonRegistrationDtoRequest.setNickname(NON_EXISTING_NICKNAME);
        validatedPersonRegistrationDtoRequest.setPassword(WEAK_PASSWORD);
        validatedPersonRegistrationDtoRequest.setMatchingPassword(WEAK_PASSWORD);
        PersonRegistrationDtoResponse personRegistrationDtoResponse = this.testObject.registerPerson(validatedPersonRegistrationDtoRequest);
        ContentPersonRegistrationErrorResponse[] error = personRegistrationDtoResponse.getContent().getError();
        Assertions.assertThat(error).hasSize(1);

        final Condition<FieldErrorResponse[]> listIsGreaterThanOne = new Condition<>(x -> x.length > 0, "array must be greater than 1.");
        ContentPersonRegistrationErrorResponse contentPersonRegistrationErrorResponse = error[0];
        FieldErrorResponse[] payload = contentPersonRegistrationErrorResponse.getPayload();
        Assertions.assertThat(payload).is(listIsGreaterThanOne);
    }


    @Test
    public void givenAPersonWantsToRegisterWithAShortPassword_whenTheyRegister_thenTheirRegistrationFails(){
        ValidatedPersonRegistrationDtoRequest validatedPersonRegistrationDtoRequest = new ValidatedPersonRegistrationDtoRequest();
        validatedPersonRegistrationDtoRequest.setEmail(NON_EXISTING_EMAIL_ADDRESS);
        validatedPersonRegistrationDtoRequest.setFirstName(FIRST_NAME);
        validatedPersonRegistrationDtoRequest.setLastName(LAST_NAME);
        validatedPersonRegistrationDtoRequest.setNickname(NON_EXISTING_NICKNAME);
        validatedPersonRegistrationDtoRequest.setPassword(PASSWORD_THAT_IS_TO_SHORT);
        validatedPersonRegistrationDtoRequest.setMatchingPassword(PASSWORD_THAT_IS_TO_SHORT);

        PersonRegistrationDtoResponse personRegistrationDtoResponse = this.testObject.registerPerson(validatedPersonRegistrationDtoRequest);

        ContentPersonRegistrationErrorResponse[] error = personRegistrationDtoResponse.getContent().getError();
        Assertions.assertThat(error).hasSize(1);

        final Condition<FieldErrorResponse[]> listIsGreaterThanOne = new Condition<>(x -> x.length > 0, "array must be greater than 1.");
        ContentPersonRegistrationErrorResponse contentPersonRegistrationErrorResponse = error[0];
        FieldErrorResponse[] payload = contentPersonRegistrationErrorResponse.getPayload();
        Assertions.assertThat(payload).is(listIsGreaterThanOne);
    }

    static volatile int COUNTER = 0;
    static String New(String str){
        return str + "_" + COUNTER++;
    }
    @Test
    public void givenAnExistingPerson_whenTheyAreUpdated_thenTheyAreUpdatedAndReturned(){
        final String NEW_FIRST_NAME = New(FIRST_NAME);
        String NEW_LAST_NAME = New(LAST_NAME);
        ValidatedUpdatePersonRegistrationDtoRequest validatedUpdatePersonRegistrationDtoRequest = ValidatedUpdatePersonRegistrationDtoRequest
            .builder()
            .firstName(NEW_FIRST_NAME)
            .lastName(NEW_LAST_NAME)
            .build();

        PersonRegistrationDtoResponse personRegistrationDtoResponse = this.testObject.updatePerson(
            UUID_OF_EXISTING_PERSON,
            validatedUpdatePersonRegistrationDtoRequest);

        Assertions.assertThat(personRegistrationDtoResponse.getContent().getSuccess()).hasSize(1);
        ContentPersonRegistrationSuccessResponse success = personRegistrationDtoResponse.getContent().getSuccess()[0];
        RegisteredPerson registeredPerson = success.getPayload();
        UUID.fromString(registeredPerson.getUuid().toString());

        Assertions.assertThat(registeredPerson.getFirstName()).isEqualTo(NEW_FIRST_NAME);
        Assertions.assertThat(registeredPerson.getLastName()).isEqualTo(NEW_LAST_NAME);

        Assertions.assertThat(personRegistrationDtoResponse.getContent().getResponseType().getCode()).isEqualTo(
            HttpStatus.OK.value());
        Assertions.assertThat(personRegistrationDtoResponse.getContent().getResponseType().getMessage()).isEqualTo(
            HttpStatus.OK.toString());
    }

}
