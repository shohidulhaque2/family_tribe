package com.shohidulhaque.user_mailbox_service.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.ContentUserMailboxSuccessResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.CreateUserMailboxDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.FromUserMailDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMail;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailUpdateReadStatusDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailbox;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailboxDtoResponse;
import com.shohidulhaque.user_mailbox_service.config.message.MessageSourceConfiguration;
import com.shohidulhaque.user_mailbox_service.config.validator.ValidatorConfiguration;
import com.shohidulhaque.user_mailbox_service.mapper.UserMailboxMapper;
import com.shohidulhaque.user_mailbox_service.repository.UserMailboxRepository;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
    ValidatorConfiguration.class,
    MessageSourceConfiguration.class,
})
public class UserMailboxServiceTest {

    UserMailboxService testObject;

    @Autowired
    Validator validator;

    @Autowired
    UserMailboxRepository userMailboxRepository;

    ObjectMapper objectMapper;
    @BeforeEach
    void beforeEachTest() {
        this.objectMapper = objectMapper();
        this.testObject = new UserMailboxService(
            this.userMailboxRepository,
            UserMailboxMapper.INSTANCE,
            this.validator,
            Clock.fixed(Instant.now(), ZoneId.of("UTC")));
    }

    public ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .registerModule(module);
    }

    @Test
    public void givenAPostToCreateANewMailbox_whenAnAttemptIsMadeToCreateIt_thenItIsSuccessfullyCreated() throws Exception {
        UUID userUuid = UUID.randomUUID();
        CreateUserMailboxDtoRequest createUserMailboxDtoRequest = new CreateUserMailboxDtoRequest(userUuid);
        UserMailboxDtoResponse userMailboxDtoResponse = this.testObject.createUserMailbox(createUserMailboxDtoRequest);
        Assertions.assertThat(userMailboxDtoResponse.getContent().getError()).hasSize(0);
        Assertions.assertThat(userMailboxDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CREATED.value());
        ContentUserMailboxSuccessResponse success = userMailboxDtoResponse.getContent().getSuccess()[0];
        Object payload = success.getPayload();
        String responseAsString = this.objectMapper.writeValueAsString(payload);
        UserMailbox userMailbox = this.objectMapper.readValue(responseAsString, UserMailbox.class);
        Assertions.assertThat(userMailbox.getUserUuid()).isEqualTo(userUuid);
    }

    @Test
    public void givenAPostToCreateANewMailboxForAnExistingSameUser_whenAnAttemptIsMadeToCreateIt_thenItIsNotCreated() {
        UUID uuidUser = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcc");
        CreateUserMailboxDtoRequest createUserMailboxDtoRequest = new CreateUserMailboxDtoRequest(uuidUser);
        UserMailboxDtoResponse userMailboxDtoResponse = this.testObject.createUserMailbox(createUserMailboxDtoRequest);
        Assertions.assertThat(userMailboxDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CONFLICT.value());
        Assertions.assertThat(userMailboxDtoResponse.getContent().getSuccess()).hasSize(0);
    }

    @Test
    public void givenNewMail_whenItIsSentToAUser_thenItIsSavedToTheirMailbox() throws Exception {
        final UUID uuidUser = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcc");
        final UUID uuidFromUser = UUID.randomUUID();
        FromUserMailDtoRequest fromUserMailDtoRequest = new FromUserMailDtoRequest();
        fromUserMailDtoRequest.setFromUserUuid(uuidFromUser);
        final String message = "Welcome to my chatspace.";
        fromUserMailDtoRequest.setMessage(message);

        UserMailboxDtoResponse userMailboxDtoResponse = this.testObject.saveMail(uuidUser, fromUserMailDtoRequest);
        Assertions.assertThat(userMailboxDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(userMailboxDtoResponse.getContent().getError()).hasSize(0);
        ContentUserMailboxSuccessResponse success = userMailboxDtoResponse.getContent().getSuccess()[0];
        Object payload = success.getPayload();
        String responseAsString = this.objectMapper.writeValueAsString(payload);
        UserMail userMail = this.objectMapper.readValue(responseAsString, UserMail.class);
        Assertions.assertThat(userMail.getMessage()).isEqualTo(message);
        Assertions.assertThat(userMail.getSeen()).isEqualTo(false);
        Assertions.assertThat(userMail.getFromUserUuid()).isEqualTo(uuidFromUser);
    }

    @Test
    public void givenAnExistingMailbox_whenTheMailboxIsRequested_thenTheMailboxIsReturned() throws Exception {
        UUID userMailboxUuid = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcc");
        UserMailboxDtoResponse userMailboxDtoResponse = this.testObject.getUserMailbox(userMailboxUuid);
        Assertions.assertThat(userMailboxDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(userMailboxDtoResponse.getContent().getError()).hasSize(0);
        ContentUserMailboxSuccessResponse success = userMailboxDtoResponse.getContent().getSuccess()[0];
        Object payload = success.getPayload();
        String responseAsString = this.objectMapper.writeValueAsString(payload);
        UserMailbox userMailbox = this.objectMapper.readValue(responseAsString, UserMailbox.class);
        Assertions.assertThat(userMailbox.getUserUuid()).isEqualTo(userMailboxUuid);
    }

    @Test
    public void givenAnExistingMail_whenItIsRetrieved_thenItIsReturned() {
        UUID userMailboxUuid = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcc");
        UUID mailUuid = UUID.fromString("e10fc759-3bb0-484c-b383-d69c138bc4e8");
        UserMailboxDtoResponse userMailboxDtoResponse = this.testObject.getMail(userMailboxUuid, mailUuid);
        Assertions.assertThat(userMailboxDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(userMailboxDtoResponse.getContent().getError()).hasSize(0);
        ContentUserMailboxSuccessResponse success = userMailboxDtoResponse.getContent().getSuccess()[0];
        Assertions.assertThat(success.getType().getMessage()).isEqualTo("User mailbox operation has been completed successfully.");
    }
    @Test
    public void givenAnExistingMail_whenTheUserHasReadIt_thenTheMailIsMarkedAsRead() throws Exception {
        UUID userMailboxUuid = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcc");
        UUID mailUuid = UUID.fromString("e10fc759-3bb0-484c-b383-d69c138bc4e8");
        UserMailUpdateReadStatusDtoRequest userMailUpdateReadStatusDtoRequest = new UserMailUpdateReadStatusDtoRequest(true);
        UserMailboxDtoResponse userMailboxDtoResponse = this.testObject.mailReadStatus(userMailboxUuid, mailUuid, userMailUpdateReadStatusDtoRequest);
        Assertions.assertThat(userMailboxDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(userMailboxDtoResponse.getContent().getError()).hasSize(0);
        ContentUserMailboxSuccessResponse success = userMailboxDtoResponse.getContent().getSuccess()[0];
        Object payload = success.getPayload();
        Assertions.assertThat(success.getType().getMessage()).isEqualTo("User mailbox operation has been completed successfully.");
        String responseAsString =  this.objectMapper.writeValueAsString(payload);
        UserMail userMail = this.objectMapper.readValue(responseAsString, UserMail.class);
        Assertions.assertThat(userMail.getSeen()).isTrue();
        Assertions.assertThat(userMail.getMessage()).isEqualTo("Some Random Messge");
    }

}
