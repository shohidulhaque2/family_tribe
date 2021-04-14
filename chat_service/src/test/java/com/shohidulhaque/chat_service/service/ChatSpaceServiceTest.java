package com.shohidulhaque.chat_service.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shohidulhaque.chat_service.config.message.MessageSourceConfiguration;
import com.shohidulhaque.chat_service.config.validator.ValidatorConfiguration;
import com.shohidulhaque.chat_service.data_model.ChatSpace;
import com.shohidulhaque.chat_service.data_model.ChatSpaceUser;
import com.shohidulhaque.chat_service.mapper.ChapSpaceInvitationMapper;
import com.shohidulhaque.chat_service.mapper.ChatSpaceDataModelToChatSpaceMapper;
import com.shohidulhaque.chat_service.mapper.ChatSpaceUserMapper;
import com.shohidulhaque.chat_service.mapper.MemberInvitationMapper;
import com.shohidulhaque.chat_service.mapper.NewMessageMapper;
import com.shohidulhaque.chat_service.repository.ChatSpaceRepository;
import com.shohidulhaque.chat_service.repository.ChatSpaceUserRepository;
import com.shohidulhaque.chat_service.repository.UserInvitationRepository;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceDtoResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceInvitation;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceInvitationDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceMessageRequestDto;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ContentChatSpaceErrorResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.CreateChatSpaceDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.CreateChatSpaceUserDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.MemberAcceptance;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.NewMessage;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({MessageSourceConfiguration.class, ValidatorConfiguration.class})
public class ChatSpaceServiceTest {

    final static String INVITEE_EMAIL = "some@email.com";

    final static UUID UuidOfExistingChatSpaceCreator = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcc");
    final static UUID UuidOfAnotherExistingChatSpaceCreator = UUID.fromString("555593c2-d736-42ce-9926-ec944b02cbcc");
    final static UUID UuidOfChatSpaceCreatedByAExistingChatSpaceCreator = UUID.fromString("6ae2995b-7c8e-41d0-95af-a76fb27834f3");
    final static UUID UuidOfInvitedPerson = UUID.fromString("555593c2-d736-42ce-9926-ec944b02cbcc");
    final static UUID UuidOfExistingInvitationForInvitedPerson = UUID.fromString("85f02cec-14e1-4fc8-8f64-f790fa8452bc");
    final static UUID UuidOfChatSpaceMember = UUID.fromString("555593c2-d736-42ce-9926-ec944b02cbcc");

    final static UUID UuidOfNonExistingSpaceCreator = UUID.fromString("d49a93ba-8605-4450-b507-a16221f8d6d5");



    ObjectMapper  objectMapper;

    ChapSpaceInvitationMapper chatSpaceInvitationMapper;

    ChatSpaceUserMapper chatSpaceUserMapper;

    ChatSpaceDataModelToChatSpaceMapper chatSpaceDataModelToChatSpaceMapper;

    MemberInvitationMapper memberInvitationMapper;

    NewMessageMapper newMessageMapper;

    Clock clock;

    @Autowired
    Validator javaxValidator;

    @Autowired
    ChatSpaceUserRepository chatSpaceUserRepository;

    @Autowired
    ChatSpaceRepository chatSpaceRepository;

    ChatSpaceService testObject;

    @SpyBean
    UserInvitationRepository userInvitationRepository;

    @MockBean
    ServiceProxy serviceProxy;

    public ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .registerModule(module);
    }

    @BeforeEach
    void beforeEachTest() {
        this.objectMapper = objectMapper();

        this.chatSpaceDataModelToChatSpaceMapper = ChatSpaceDataModelToChatSpaceMapper.INSTANCE;
        this.chatSpaceUserMapper = ChatSpaceUserMapper.INSTANCE;
        this.chatSpaceInvitationMapper = ChapSpaceInvitationMapper.INSTANCE;
        this.memberInvitationMapper = MemberInvitationMapper.INSTANCE;
        this.newMessageMapper = NewMessageMapper.INSTANCE;
        this.testObject = new ChatSpaceService(
            this.chatSpaceUserRepository,
            this.chatSpaceRepository,
            this.javaxValidator,
            this.userInvitationRepository,
            this.serviceProxy,
            this.chatSpaceInvitationMapper,
            this.chatSpaceUserMapper,
            this.chatSpaceDataModelToChatSpaceMapper,
            this.memberInvitationMapper,
            this.newMessageMapper,
            Clock.fixed(Instant.now(), ZoneId.of("UTC")));
    }

    @Test
    public void givenANewChatSpaceUser_whenTheChatSpaceUserIsAdded_theTheUserSpaceUserIsSaved() throws Exception{
        CreateChatSpaceUserDtoRequest createChatSpaceUserDtoRequest = new CreateChatSpaceUserDtoRequest(UuidOfNonExistingSpaceCreator);
        ChatSpaceDtoResponse chatSpaceDtoResponse = this.testObject.addNewChatSpaceUser(createChatSpaceUserDtoRequest);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).isEmpty();
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).isNotEmpty();
        Object payload =  chatSpaceDtoResponse.getContent().getSuccess()[0].getPayload();
        String responseAsString = this.objectMapper.writeValueAsString(payload);
        com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceUser chatSpaceUser = this.objectMapper.readValue(responseAsString, com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceUser.class);
        Assertions.assertThat(chatSpaceUser.getUuid()).isNotNull();
        Assertions.assertThat(chatSpaceUser.getChatSpace()).hasSize(0);
    }

    @Test
    public void givenAnExistingChatSpaceUser_whenTheChatSpaceUserIsAdded_theTheUserSpaceUserIsNotCreated() throws Exception{
        CreateChatSpaceUserDtoRequest createChatSpaceUserDtoRequest = new CreateChatSpaceUserDtoRequest(UuidOfExistingChatSpaceCreator);
        ChatSpaceDtoResponse chatSpaceDtoResponse = this.testObject.addNewChatSpaceUser(createChatSpaceUserDtoRequest);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CONFLICT.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).isEmpty();
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).isNotEmpty();
        ContentChatSpaceErrorResponse contentChatSpaceErrorResponse = chatSpaceDtoResponse.getContent().getError()[0];
        Assertions.assertThat(contentChatSpaceErrorResponse.getType().getMessage()).isEqualTo("Unable to create new chat space.");
    }

    @Test
    public void givenAnExistingChatSpace_whenItIsRetrieved_thenItIsReturned() throws Exception{
        ChatSpaceDtoResponse chatSpaceDtoResponse = this.testObject.getChatSpaceUser(UuidOfExistingChatSpaceCreator);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).isEmpty();
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).hasSize(1);
        Object payload =  chatSpaceDtoResponse.getContent().getSuccess()[0].getPayload();
        String responseAsString = this.objectMapper.writeValueAsString(payload);
        com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceUser chatSpaceUser = this.objectMapper.readValue(responseAsString, com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceUser.class);
        Assertions.assertThat(chatSpaceUser.getUuid()).isEqualTo(UUID.fromString("25c2d3bf-7a72-4ad7-8a40-8cbd7526150c"));
    }

    @Test
    public void givenAnExistingUserThatHasChatSpaces_whenTheChatSpacesAreRetrieved_thenTheyAreReturned() throws Exception{
        ChatSpaceDtoResponse chatSpaceDtoResponse =  this.testObject.getChatSpaceUser(UuidOfExistingChatSpaceCreator);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.OK.value());
        Object payload = chatSpaceDtoResponse.getContent().getSuccess()[0].getPayload();
        String responseAsJson = this.objectMapper.writeValueAsString(payload);
        ChatSpaceUser chatSpaceUser = this.objectMapper.readValue(responseAsJson, ChatSpaceUser.class);
        List<ChatSpace> chatSpaceList = chatSpaceUser.getChatSpace();
        Assertions.assertThat(chatSpaceList).isNotEmpty();
        List<UUID> chatSpaceUuidList = chatSpaceList.stream().map(chatSpace -> chatSpace.getUuid()).collect(Collectors.toList());
        Assertions.assertThat(chatSpaceUuidList)
                .contains(UUID.fromString("6ae2995b-7c8e-41d0-95af-a76fb27834f3"),
                            UUID.fromString("b1c17f65-7a28-422c-a838-ae2c9ed1404b"));
    }

    @Test
    public void givenAnExistingChatSpaceUser_whenANewChatSpaceIsAdded_thenItIsAddedToTheirChatSpaces() throws Exception {
        final String NEW_CHATSPACE_NAME = "New ChatSpace";
        CreateChatSpaceDtoRequest createChatSpaceDtoRequest = new CreateChatSpaceDtoRequest(NEW_CHATSPACE_NAME);
        ChatSpaceDtoResponse chatSpaceDtoResponse  = this.testObject.addChatSpace(UuidOfExistingChatSpaceCreator, createChatSpaceDtoRequest);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CREATED.value());
        Object payload = chatSpaceDtoResponse.getContent().getSuccess()[0].getPayload();
        String responseAsJson = this.objectMapper.writeValueAsString(payload);
        com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpace chatSpace = this.objectMapper.readValue(responseAsJson, com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpace.class);
        Assertions.assertThat(chatSpace.getName()).isEqualTo(NEW_CHATSPACE_NAME);
    }

    @Test
    public void givenAnExistingChatSpaceUser_whenANewChatSpaceWithAnExistingNameIsAdded_thenItIsNotAdded() {
        final String EXISTING_CHATSPACE_NAME = "ayl";
        CreateChatSpaceDtoRequest createChatSpaceDtoRequest = new CreateChatSpaceDtoRequest(EXISTING_CHATSPACE_NAME);
        ChatSpaceDtoResponse chatSpaceDtoResponse  = this.testObject.addChatSpace(UuidOfExistingChatSpaceCreator, createChatSpaceDtoRequest);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CONFLICT.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).hasSize(0);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).hasSize(1);
        ContentChatSpaceErrorResponse contentChatSpaceErrorResponse = chatSpaceDtoResponse.getContent().getError()[0];
        ErrorType type = contentChatSpaceErrorResponse.getType();
        Assertions.assertThat(type.getMessage()).isEqualTo("Unable to create new chat space.");
    }

    @Test
    public void givenAnExistingChatSpace_whenAnInvitationIsMadeToInviteAnotherPerson_thenAnInviteIsCreatedAndAndSentToThem() throws IOException {
        //UUID inviteeUuid = ExistingChatSpaceCreatorUuidTwo;
        Mockito.when(this.serviceProxy.doesMemberExist(INVITEE_EMAIL)).thenReturn(Optional.of(
            UuidOfAnotherExistingChatSpaceCreator));
        Mockito.when(this.serviceProxy.sendInvitationToUser(Mockito.eq(
            UuidOfAnotherExistingChatSpaceCreator), Mockito.any())).thenReturn(true);
        ChatSpaceInvitationDtoRequest chatSpaceInvitationDtoRequest = ChatSpaceInvitationDtoRequest
                                                                        .builder()
                                                                        .chatSpaceUuid(
                                                                            UuidOfChatSpaceCreatedByAExistingChatSpaceCreator)
                                                                        .email(INVITEE_EMAIL)
                                                                        .build();
        ChatSpaceDtoResponse result = this.testObject.createInvitation(UuidOfExistingChatSpaceCreator, chatSpaceInvitationDtoRequest);
        Assertions.assertThat(result.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CREATED.value());
        Object payload = result.getContent().getSuccess()[0].getPayload();
        String responseAsJson = this.objectMapper.writeValueAsString(payload);
        ChatSpaceInvitation chatSpaceInvitation = this.objectMapper.readValue(responseAsJson, ChatSpaceInvitation.class);
        Assertions.assertThat(chatSpaceInvitation.getUuid()).isNotNull();
        Mockito.verify(this.serviceProxy, Mockito.times(1)).doesMemberExist(INVITEE_EMAIL);
        Mockito.verify(this.serviceProxy, Mockito.times(1)).sendInvitationToUser(Mockito.eq(
            UuidOfAnotherExistingChatSpaceCreator), Mockito.any());
        //Mockito.verify(this.chatSpaceUserRepository).save(Mockito.any(ChatSpaceUser.class)); probably caused by Mockito generating from a final class.
    }


    @Test
    public void givenAnExistingInvitationToAPerson_whenTheyAcceptTheInvitation_thenTheInvitationIsMarkedAsAcceptedAndTheyAreAddedAsAMemberToTheChatSpace() throws Exception{
        ChatSpaceDtoResponse chatSpaceDtoResponse = this.testObject.acceptInvitation(
            UuidOfExistingChatSpaceCreator,
            UuidOfChatSpaceCreatedByAExistingChatSpaceCreator,
            UuidOfExistingInvitationForInvitedPerson,
            UuidOfInvitedPerson);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).hasSize(0);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).hasSize(1);
        Object payload = chatSpaceDtoResponse.getContent().getSuccess()[0].getPayload();
        String responseAsJson = this.objectMapper.writeValueAsString(payload);
        MemberAcceptance chatSpaceInvitation = this.objectMapper.readValue(responseAsJson, MemberAcceptance.class);
        Assertions.assertThat(chatSpaceInvitation.getMemberUuid()).isEqualTo(UuidOfInvitedPerson);
        Assertions.assertThat(chatSpaceInvitation.getChatSpaceUuid()).isEqualTo(UuidOfChatSpaceCreatedByAExistingChatSpaceCreator);
        Assertions.assertThat(chatSpaceInvitation.getUuid()).isNotNull();
    }

    @Test
    public void givenAnExistingChatSpace_whenAMemberAddsANewMessage_thenThemMessageIsAddedSuccessfully() throws Exception {

        final String MESSAGE = "THIS IS THE NEW MESSAGE";

        ChatSpaceMessageRequestDto chatSpaceMessageRequestDto = ChatSpaceMessageRequestDto
            .builder()
            .uuidOfMember(UuidOfChatSpaceMember)
            .message(MESSAGE)
            .build();

        ChatSpaceDtoResponse chatSpaceDtoResponse = this.testObject.addMessage(
            UuidOfExistingChatSpaceCreator,
            UuidOfChatSpaceCreatedByAExistingChatSpaceCreator,
            chatSpaceMessageRequestDto);

        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).hasSize(0);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).hasSize(1);
        Object payload = chatSpaceDtoResponse.getContent().getSuccess()[0].getPayload();
        String responseAsJson = this.objectMapper.writeValueAsString(payload);
        NewMessage newMessage = this.objectMapper.readValue(responseAsJson, NewMessage.class);
        Assertions.assertThat(newMessage.getUuid()).isNotNull();
        Assertions.assertThat(newMessage.getCreatedTimestamp()).isNotNull();
    }

    //TODO: impplement
    @Disabled
    public void givenAnExistingInvitationToAPerson_whenTheyRejectTheInvitation_thenTheInvitationIsMarkedAsAcceptedAsRejected(){
        boolean result = this.testObject.rejectInvitation(UuidOfExistingChatSpaceCreator, UuidOfInvitedPerson, UuidOfExistingInvitationForInvitedPerson);
        Assertions.assertThat(result).isTrue();
    }

    //TODO: impplement
    @Disabled
    public void givenAnExistingInvitationToAPerson_whenItIsDeleted_thenTheInvitationIsRemoved(){
        boolean result = this.testObject.deleteInvitation(UuidOfExistingChatSpaceCreator, UuidOfInvitedPerson, UuidOfExistingInvitationForInvitedPerson);
        Assertions.assertThat(result).isTrue();
    }
}
