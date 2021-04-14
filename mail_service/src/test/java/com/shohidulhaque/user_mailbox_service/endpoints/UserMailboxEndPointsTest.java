package com.shohidulhaque.user_mailbox_service.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.CreateUserMailboxDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.FromUserMailDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMail;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailUpdateReadStatusDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailbox;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailboxDtoResponse;
import com.shohidulhaque.my_people.common_model.http.ContentType;
import com.shohidulhaque.user_mailbox_service.repository.UserMailboxRepository;
import com.shohidulhaque.user_mailbox_service.service.UserMailboxService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
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
@WebMvcTest(UserMailboxEndPoints.class)
public class UserMailboxEndPointsTest {

    UUID UUID_OF_EXISTING_MAILBOX = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcc");
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserMailboxService userMailboxService;

    @Autowired
    UserMailboxRepository userMailboxRepository;
    //==============================================================================================
    @Test
    public void givenAUser_whenAPostRequestToCreateAMailbox_ThenItIsCreated_And_CreatedStatusIsGiven() throws Exception{
        CreateUserMailboxDtoRequest createUserMailboxDtoRequest = new CreateUserMailboxDtoRequest();
        createUserMailboxDtoRequest.setUserUuid(UUID.randomUUID());
        String serializedRequest = this.objectMapper.writeValueAsString(createUserMailboxDtoRequest);
        this.mockMvc
            .perform(MockMvcRequestBuilders
                        .post("/api/v1/user-mailbox-service/users/mailbox")
                        .content(serializedRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.ContentType));
    }
    //==============================================================================================
    @Test
    public void givenAnExistingMailbox_whenAGetRequestIsMadeToRetrieveIt_ThenItIsReturned_And_OkStatusIsGiven() throws Exception{

        String responseAsJson = this.mockMvc
                            .perform(MockMvcRequestBuilders
                                .get("/api/v1/user-mailbox-service/users/mailbox/{userUuid}",  UUID_OF_EXISTING_MAILBOX)
                                .with(SecurityMockMvcRequestPostProcessors.jwt()))
                            .andExpect(MockMvcResultMatchers.status().isOk())
                            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.ContentType))
                            .andReturn().getResponse().getContentAsString();

        UserMailboxDtoResponse userMailboxDtoResponse = this.objectMapper.readValue(responseAsJson, UserMailboxDtoResponse.class);

        Assertions.assertThat(userMailboxDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(userMailboxDtoResponse.getContent().getError()).isEmpty();
        Assertions.assertThat(userMailboxDtoResponse.getContent().getSuccess()).isNotEmpty();

        Assertions.assertThat(userMailboxDtoResponse.getContent().getSuccess()[0].getPayload()).isNotNull();
        Map userMailboxMap = (Map)userMailboxDtoResponse.getContent().getSuccess()[0].getPayload();
        UserMailbox userMailbox = this.objectMapper.convertValue(userMailboxMap, UserMailbox.class);
        Assertions.assertThat(userMailbox.getUserUuid()).isEqualTo(UUID_OF_EXISTING_MAILBOX);
        Assertions.assertThat(userMailbox.getCreationTime()).isNotNull();
        Assertions.assertThat(userMailbox.getMail()).isNotNull();
    }
    //==============================================================================================
    @Test
    public void givenAnExistingMailboxWithMail_whenAGetRequestIsMadeToRetrieveIt_ThenItIsReturned_And_OkStatusIsGiven() throws Exception{

        FromUserMailDtoRequest fromUserMailDtoRequest = new FromUserMailDtoRequest();
        UUID fromUserUuid = UUID.randomUUID();
        fromUserMailDtoRequest.setFromUserUuid(fromUserUuid);
        String uniqueMessage = UUID.randomUUID().toString();
        fromUserMailDtoRequest.setMessage(uniqueMessage);

        this.userMailboxService.saveMail(UUID_OF_EXISTING_MAILBOX, fromUserMailDtoRequest);

        String responseAsJson = this.mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/v1/user-mailbox-service/users/mailbox/{userUuid}", UUID_OF_EXISTING_MAILBOX)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.ContentType))
            .andReturn().getResponse().getContentAsString();

        UserMailboxDtoResponse userMailboxDtoResponse = this.objectMapper.readValue(responseAsJson, UserMailboxDtoResponse.class);

        Assertions.assertThat(userMailboxDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(userMailboxDtoResponse.getContent().getError()).isEmpty();
        Assertions.assertThat(userMailboxDtoResponse.getContent().getSuccess()).isNotEmpty();
        Assertions.assertThat(userMailboxDtoResponse.getContent().getSuccess()).hasSize(1);
        Assertions.assertThat(userMailboxDtoResponse.getContent().getSuccess()[0].getPayload()).isNotNull();
        Map userMailboxMap = (Map)userMailboxDtoResponse.getContent().getSuccess()[0].getPayload();
        UserMailbox userMailbox = this.objectMapper.convertValue(userMailboxMap, UserMailbox.class);
        Assertions.assertThat(userMailbox.getUserUuid()).isEqualTo(UUID_OF_EXISTING_MAILBOX);
        Assertions.assertThat(userMailbox.getCreationTime()).isNotNull();
        Assertions.assertThat(userMailbox.getMail()).isNotNull();

        List<UserMail> userMailList = Arrays.stream(userMailbox.getMail()).filter(mail -> mail.getFromUserUuid().equals(fromUserUuid)).collect(Collectors.toList());
        Assertions.assertThat(userMailList).hasSize(1);
        SoftAssertions assertions = new SoftAssertions();
        UserMail userMail = userMailList.get(0);
        assertions.assertThat(userMail.getFromUserUuid()).isEqualTo(fromUserUuid);
        assertions.assertThat(userMail.getMessage()).isEqualTo(uniqueMessage);
        assertions.assertAll();
    }
    //==============================================================================================
    @Test
    public void givenAnExistingMailbox_whenThereIsNewMailForIt_thenItIsSavedInTheMailbox() throws Exception{

        UUID fromUserUuid = UUID.randomUUID();

        FromUserMailDtoRequest fromUserMailDtoRequest = new FromUserMailDtoRequest();
        fromUserMailDtoRequest.setFromUserUuid(fromUserUuid);
        UUID uniqueMessage = UUID.randomUUID();
        fromUserMailDtoRequest.setMessage(uniqueMessage.toString());

        String serializedRequest = this.objectMapper.writeValueAsString(fromUserMailDtoRequest);
        String resultAsJson = this.mockMvc
            .perform(MockMvcRequestBuilders
                .post("/api/v1/user-mailbox-service/users/mailbox/{toUserUuid}/mail", UUID_OF_EXISTING_MAILBOX)
                .content(serializedRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.ContentType))
            .andReturn().getResponse().getContentAsString();

        UserMailboxDtoResponse userMailboxDtoResponse = this.objectMapper.readValue(resultAsJson, UserMailboxDtoResponse.class);
        Assertions.assertThat(userMailboxDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(userMailboxDtoResponse.getContent().getError()).isEmpty();
        Assertions.assertThat(userMailboxDtoResponse.getContent().getSuccess()).isNotEmpty();
        Assertions.assertThat(userMailboxDtoResponse.getContent().getSuccess()).hasSize(1);
        Assertions.assertThat(userMailboxDtoResponse.getContent().getSuccess()[0].getPayload()).isNotNull();
        Map userMailMap = (Map)userMailboxDtoResponse.getContent().getSuccess()[0].getPayload();
        UserMail userMail = this.objectMapper.convertValue(userMailMap, UserMail.class);
        Assertions.assertThat(userMail.getUuid()).isNotNull();
        Assertions.assertThat(userMail.getCreationTime()).isNotNull();
        Assertions.assertThat(userMail.getMessage()).isEqualTo(uniqueMessage.toString());
    }
    //==============================================================================================
    @Test
    public void givenAnExistingMail_whenItHasBeenRead_ThenItIsSuccessfullyMarkedAsRead_And_OkStatusIsGiven() throws Exception{

        UUID mailUuid = UUID.fromString("e10fc759-3bb0-484c-b383-d69c138bc4e8");

        UserMailUpdateReadStatusDtoRequest userMailUpdateReadStatusDtoRequest = new UserMailUpdateReadStatusDtoRequest();
        userMailUpdateReadStatusDtoRequest.setRead(true);

        String serializedRequest = this.objectMapper.writeValueAsString(userMailUpdateReadStatusDtoRequest);

        String resultAsJson = this.mockMvc
            .perform(MockMvcRequestBuilders
                .patch("/api/v1/user-mailbox-service/users/mailbox/{userUuid}/mail/{mailUuid}" , UUID_OF_EXISTING_MAILBOX, mailUuid)
                .content(serializedRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.ContentType))
            .andReturn().getResponse().getContentAsString();

        UserMailboxDtoResponse userMailboxDtoResponse = this.objectMapper.readValue(resultAsJson, UserMailboxDtoResponse.class);
        Assertions.assertThat(userMailboxDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(userMailboxDtoResponse.getContent().getError()).isEmpty();
        Assertions.assertThat(userMailboxDtoResponse.getContent().getSuccess()).isNotEmpty();
        Assertions.assertThat(userMailboxDtoResponse.getContent().getSuccess()).hasSize(1);
        Assertions.assertThat(userMailboxDtoResponse.getContent().getSuccess()[0].getPayload()).isNotNull();
        Object userMailMap = userMailboxDtoResponse.getContent().getSuccess()[0].getPayload();
        UserMail userMail = this.objectMapper.convertValue(userMailMap, UserMail.class);
        Assertions.assertThat(userMail.getSeen()).isTrue();
    }
    //==============================================================================================
}
