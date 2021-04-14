package com.shohidulhaque.user_registry_service.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.User;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.ContentPersonRegistration;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.ContentPersonRegistrationErrorResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.ContentPersonRegistrationSuccessResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.PersonRegistrationDtoResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.get.RegisteredPerson;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.patch.UpdatePersonRegistrationDtoRequest;
import com.shohidulhaque.my_people.common_model.http.ContentType;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.user_registry_service.config.authorization_server.openid_connect_1.okta.OktaConstants;
import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedPersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.service.registration.listener.OpenIdConnect2Proxy;
import com.shohidulhaque.user_registry_service.service.user_invitation.MailSender;
import java.util.Map;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

//==================================================================================================
//experimental - add a jpa layer underneath the web layer for testing.
@ActiveProfiles("development")
@OverrideAutoConfiguration(enabled = false)
@TypeExcludeFilters(DataJpaTypeExcludeFilter.class)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestEntityManager
@ImportAutoConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//==================================================================================================
@WebMvcTest(RegistrationRestController.class)
public class RegistrationRestControllerTest {
    //==============================================================================================
    @Autowired
    MockMvc  mockMvc;
    //==============================================================================================
    @Autowired
    ObjectMapper objectMapper;
    //==============================================================================================
    @MockBean
    MailSender mailSender;
    //==============================================================================================
    @MockBean
    OpenIdConnect2Proxy openIdConnect2Proxy;
    //==============================================================================================
    @MockBean
    Client oktaClient;
    //==============================================================================================

    @BeforeEach
    public void beforeEachTest(){

    }

//    static String NON_EXISTING_EMAIL = "ali@baba.com";
//    static String NON_EXISTING_NICKNAME = "nickname";
//    static String NON_EXISTING_FIRSTNAME = "blah";
//    static String NON_EXISTING_LASTNAME = "blahblah";
//
//    static String EXISTING_EMAIL = "email@email.com";
//    static String EXISTING_NICKNAME = "nickname";
//    static String EXISTING_FIRSTNAME = "firstname";
//    static String EXISTING_LASTNAME = "lastname";
//
//    static String STRONG_PASSWORD = "ds^$%76kkiiis32d";
//    static String WEAK_PASSWORD = "sdfs";
//    static String NON_MATCHING_PASSWORD = "DS^$%76kkiiis32d";

    //==============================================================================================
    static final UUID UUID_OF_EXISTING_PERSON = UUID.fromString("ee3c41a0-1d7a-4082-816a-5143884bd37d");
    static final String EXISTING_NICKNAME = "nickname";
    static final String EXISTING_EMAIL_ADDRESS = "email@email.com";
    static final String EXISTING_FIRST_NAME = "shohidul";
    static final String EXISTING_LAST_NAME = "haque";
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
    static final String NEW_FIRST_NAME = "new_ah!";
    static final String NEW_LAST_NAME = "new_no";
    //==============================================================================================
    @Test
    public void givenANewPerson_whenAPostIsUsedToCreateANewPerson_thenThePersonIsCreatedAndAHttpCreatedIsReturned()
    throws Exception {

        User userMock = Mockito.mock(User.class);
        Mockito.when(userMock.getId()).thenReturn(USER_ID_FROM_OAUTH2_SERVER);
        Map mockMap = Map.of(
            OktaConstants.ACTIVATE_LINK, Map.of(OktaConstants.ACTIVE_LINK_HREF, VALIDATION_LINK));
        Mockito.when(userMock.getLinks()).thenReturn(mockMap);

        Mockito.when(openIdConnect2Proxy
            .registerUserWithOAuthServer(
                Mockito.any(ValidatedPersonRegistrationDtoRequest.class),
                Mockito.anyString(),
                Mockito.anySet(),
                Mockito.any(UUID.class)))
            .thenReturn(userMock);

        ValidatedPersonRegistrationDtoRequest validatedPersonRegistrationDtoRequest = ValidatedPersonRegistrationDtoRequest
                                                                        .builder()
                                                                        .email(NON_EXISTING_EMAIL_ADDRESS)
                                                                        .firstName(FIRST_NAME)
                                                                        .lastName(LAST_NAME)
                                                                        .nickname(NON_EXISTING_NICKNAME)
                                                                        .password(STRONG_PASSWORD)
                                                                        .matchingPassword(STRONG_PASSWORD)
                                                                        .build();
        String requestAsJson = this.objectMapper.writeValueAsString(validatedPersonRegistrationDtoRequest);
        String responseAsJson = this.mockMvc
            .perform(MockMvcRequestBuilders
                .post("/api/v1/user-registry-service/person")
                .content(requestAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.anonymous()))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.ContentType))
            .andReturn().getResponse().getContentAsString();
        PersonRegistrationDtoResponse response = this.objectMapper.readValue(responseAsJson, PersonRegistrationDtoResponse.class);
        ContentPersonRegistration contentPersonRegistration = response.getContent();
        Assertions.assertThat(contentPersonRegistration.getResponseType().getCode()).isEqualTo(HttpStatus.CREATED.value());
        ContentPersonRegistrationSuccessResponse[] successArray = contentPersonRegistration.getSuccess();
        Assertions.assertThat(successArray).hasSize(1);
        ContentPersonRegistrationSuccessResponse contentPersonRegistrationSuccessResponse = successArray[0];
        RegisteredPerson registeredPerson = contentPersonRegistrationSuccessResponse.getPayload();
        Assertions.assertThat(registeredPerson.getEmail()).isEqualTo(NON_EXISTING_EMAIL_ADDRESS);
        Assertions.assertThat(registeredPerson.getNickname()).isEqualTo(NON_EXISTING_NICKNAME);
        Assertions.assertThat(registeredPerson.getFirstName()).isEqualTo(FIRST_NAME);
        Assertions.assertThat(registeredPerson.getLastName()).isEqualTo(LAST_NAME);
        UUID.fromString(registeredPerson.getUuid().toString());

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
    public void givenAnExistingPerson_whenAPostIsUsedToCreateANewPersonUsingAnExistingEmail_thenAHttpBadRequestReturned()
        throws Exception {

        User userMock = Mockito.mock(User.class);
        Mockito.when(userMock.getId()).thenReturn(USER_ID_FROM_OAUTH2_SERVER);
        Map mockMap = Map.of(
            OktaConstants.ACTIVATE_LINK, Map.of(OktaConstants.ACTIVE_LINK_HREF, VALIDATION_LINK));
        Mockito.when(userMock.getLinks()).thenReturn(mockMap);

        Mockito.when(openIdConnect2Proxy
            .registerUserWithOAuthServer(
                Mockito.any(ValidatedPersonRegistrationDtoRequest.class),
                Mockito.anyString(),
                Mockito.anySet(),
                Mockito.any(UUID.class)))
            .thenReturn(userMock);

        ValidatedPersonRegistrationDtoRequest validatedPersonRegistrationDtoRequest = ValidatedPersonRegistrationDtoRequest
            .builder()
            .email(EXISTING_EMAIL_ADDRESS)
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .nickname(NON_EXISTING_NICKNAME)
            .password(STRONG_PASSWORD)
            .matchingPassword(STRONG_PASSWORD)
            .build();
        String requestAsJson = this.objectMapper.writeValueAsString(validatedPersonRegistrationDtoRequest);
        String responseAsJson = this.mockMvc
            .perform(MockMvcRequestBuilders
                .post("/api/v1/user-registry-service/person")
                .content(requestAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.anonymous()))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.ContentType))
            .andReturn().getResponse().getContentAsString();
        PersonRegistrationDtoResponse response = this.objectMapper.readValue(responseAsJson, PersonRegistrationDtoResponse.class);
        ContentPersonRegistration contentPersonRegistration = response.getContent();
        Assertions.assertThat(contentPersonRegistration.getResponseType().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        ContentPersonRegistrationSuccessResponse[] successArray = contentPersonRegistration.getSuccess();
        Assertions.assertThat(successArray).hasSize(0);
        ContentPersonRegistrationErrorResponse[] errorArray = contentPersonRegistration.getError();
        Assertions.assertThat(errorArray).hasSize(1);
        ErrorType type = errorArray[0].getType();
        Assertions.assertThat(type.getMessage()).isEqualTo("Email already exist.");

        Mockito.verify(
            openIdConnect2Proxy, Mockito.times(0)).
            registerUserWithOAuthServer(
                Mockito.any(ValidatedPersonRegistrationDtoRequest.class),
                Mockito.anyString(),
                Mockito.anySet(),
                Mockito.any(UUID.class));
        Mockito.verify(userMock, Mockito.times(0)).getLinks();
        Mockito.verify(userMock, Mockito.times(0)).getId();
    }


    @Test
    public void givenAnExistingPerson_whenAHttpGetIsUsedToRetrieveThem_thenTheyAreReturnedAndAHttpOkIsReturned()
        throws Exception {
        String responseAsJson = this.mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/user-registry-service/person/{uuidOfPerson}", UUID_OF_EXISTING_PERSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.ContentType))
            .andReturn().getResponse().getContentAsString();
        PersonRegistrationDtoResponse response = this.objectMapper.readValue(responseAsJson, PersonRegistrationDtoResponse.class);
        ContentPersonRegistration contentPersonRegistration = response.getContent();
        Assertions.assertThat(contentPersonRegistration.getResponseType().getCode()).isEqualTo(HttpStatus.OK.value());
        ContentPersonRegistrationSuccessResponse[] successArray = contentPersonRegistration.getSuccess();
        Assertions.assertThat(successArray).hasSize(1);
        ContentPersonRegistrationSuccessResponse contentPersonRegistrationSuccessResponse = successArray[0];
        RegisteredPerson registeredPerson = contentPersonRegistrationSuccessResponse.getPayload();
        Assertions.assertThat(registeredPerson.getEmail()).isEqualTo(EXISTING_EMAIL_ADDRESS);
        Assertions.assertThat(registeredPerson.getNickname()).isEqualTo(EXISTING_NICKNAME);
        Assertions.assertThat(registeredPerson.getFirstName()).isEqualTo(EXISTING_FIRST_NAME);
        Assertions.assertThat(registeredPerson.getLastName()).isEqualTo(EXISTING_LAST_NAME);
        UUID.fromString(registeredPerson.getUuid().toString());
    }

    @Test
    public void givenAnExistingPerson_whenAPatchIsUsedToUpdateTheirDetails_thenTheirDetailsAreUpdatedAndAHttpOkIsReturned()
    throws Exception {
        UpdatePersonRegistrationDtoRequest updatePersonRegistrationDtoRequest = UpdatePersonRegistrationDtoRequest
            .builder()
            .firstName(NEW_FIRST_NAME)
            .lastName(NEW_LAST_NAME)
            .build();
        String requestAsJson = this.objectMapper.writeValueAsString(updatePersonRegistrationDtoRequest);
        String responseAsJson = this.mockMvc
            .perform(MockMvcRequestBuilders
                .put("/api/v1/user-registry-service/person/{uuidOfPerson}", UUID_OF_EXISTING_PERSON)
                .content(requestAsJson)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.ContentType))
            .andReturn().getResponse().getContentAsString();
        PersonRegistrationDtoResponse response = this.objectMapper.readValue(responseAsJson, PersonRegistrationDtoResponse.class);
        ContentPersonRegistration contentPersonRegistration = response.getContent();
        Assertions.assertThat(contentPersonRegistration.getResponseType().getCode()).isEqualTo(HttpStatus.OK.value());
        ContentPersonRegistrationSuccessResponse[] successArray = contentPersonRegistration.getSuccess();
        Assertions.assertThat(successArray).hasSize(1);
        ContentPersonRegistrationSuccessResponse contentPersonRegistrationSuccessResponse = successArray[0];
        RegisteredPerson registeredPerson = contentPersonRegistrationSuccessResponse.getPayload();
        Assertions.assertThat(registeredPerson.getFirstName()).isEqualTo(NEW_FIRST_NAME);
        Assertions.assertThat(registeredPerson.getLastName()).isEqualTo(NEW_LAST_NAME);
        Assertions.assertThat(registeredPerson.getUuid()).isEqualTo(UUID_OF_EXISTING_PERSON);
        UUID.fromString(registeredPerson.getUuid().toString());
    }

}
