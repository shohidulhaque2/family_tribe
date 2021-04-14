package com.shohidulhaque.chat_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceDtoResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceMessageRequestDto;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ContentChatSpaceErrorResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ContentChatSpaceSuccessResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.CreateChatSpaceDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.CreateChatSpaceUserDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.NewMessage;
import com.shohidulhaque.my_people.common_model.http.ContentType;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("integration")
@Testcontainers
@SpringBootTest( properties = {"spring.cloud.discovery.enabled = false"})
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ChatServiceIntegrationSpecification.Initializer.class)
public class ChatServiceIntegrationSpecification {

    public static MySQLContainer staticSqlContainer;
    public static org.slf4j.Logger Logger = LoggerFactory.getLogger(ChatServiceIntegrationSpecification.class);
    static {
        staticSqlContainer = new MySQLContainer()
            .withDatabaseName("chat_service")
            .withUsername("user")
            .withPassword("password");
        staticSqlContainer.waitingFor(Wait.forLogMessage("Awaiting Connection.*", 1));
        staticSqlContainer.withExposedPorts(3306)
            .withLogConsumer(new Slf4jLogConsumer(Logger));
        staticSqlContainer.start();
        Logger.debug(staticSqlContainer.getContainerId() + " has mapped port container port 3306 to " + staticSqlContainer.getMappedPort(3306).toString());
        Logger.info(staticSqlContainer.getJdbcUrl());
    }
    static class Initializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=" + staticSqlContainer.getJdbcUrl(),
                "spring.datasource.username=" + staticSqlContainer.getUsername(),
                "spring.datasource.password=" + staticSqlContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    enum ContentType {
        Application_Hal_Json("application/hal+json");

        ContentType(String  contentType){
            this.contentType = contentType;
        }

        final String contentType;
    }

    final static String INVITEE_EMAIL = "some@email.com";

    final static UUID UuidOfExistingChatSpaceCreator = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcc");
    final static UUID UuidOfExistingChatSpace = UUID.fromString("6ae2995b-7c8e-41d0-95af-a76fb27834f3");
    final static UUID UuidOfAnotherExistingChatSpaceCreator = UUID.fromString("555593c2-d736-42ce-9926-ec944b02cbcc");
    final static UUID UuidOfChatSpaceCreatedByAExistingChatSpaceCreator = UUID.fromString("6ae2995b-7c8e-41d0-95af-a76fb27834f3");
    final static UUID UuidOfInvitedPerson = UUID.fromString("555593c2-d736-42ce-9926-ec944b02cbcc");
    final static UUID UuidOfExistingInvitationForInvitedPerson = UUID.fromString("85f02cec-14e1-4fc8-8f64-f790fa8452bc");
    final static UUID UuidOfNonExistingSpaceCreator = UUID.fromString("d49a93ba-8605-4450-b507-a16221f8d6d5");
    final static String ExistingChatSpaceName = "ayl";
    final static UUID UuidOfChatSpaceMember = UUID.fromString("555593c2-d736-42ce-9926-ec944b02cbcc");

    @Test
    public void givenANewUser_whenANewChatSpaceUserCreated_thenANewChatSpaceIsCreatedAndReturned() throws Exception{

        UUID chatSpaceCreatorUuid = UuidOfNonExistingSpaceCreator;
        CreateChatSpaceUserDtoRequest createChatSpaceUserDtoRequest = new CreateChatSpaceUserDtoRequest(chatSpaceCreatorUuid);
        String request = this.objectMapper.writeValueAsString(createChatSpaceUserDtoRequest);

        String responseAsString = this.mockMvc.
            perform(MockMvcRequestBuilders
                .post("/api/v1/chat-space-service/chatspaces")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.contentType))
            .andReturn().getResponse().getContentAsString();

        ChatSpaceDtoResponse chatSpaceDtoResponse = this.objectMapper.readValue(responseAsString, ChatSpaceDtoResponse.class);

        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).isEmpty();
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).isNotEmpty();
        Object payload =  chatSpaceDtoResponse.getContent().getSuccess()[0].getPayload();
        responseAsString = this.objectMapper.writeValueAsString(payload);
        com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceUser chatSpaceUser = this.objectMapper.readValue(responseAsString, com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceUser.class);
        Assertions.assertThat(chatSpaceUser.getUuid()).isNotNull();
        Assertions.assertThat(chatSpaceUser.getChatSpace()).hasSize(0);
    }

    @Test
    public void givenAnExistingChatSpaceUser_whenAnAttemptIsMadeToCreateThemAgain_thenTheyAreNotCreatedAndAHttpConflictIsReturned() throws Exception{

        UUID chatSpaceCreatorUuid = UuidOfExistingChatSpaceCreator;
        CreateChatSpaceUserDtoRequest createChatSpaceUserDtoRequest = new CreateChatSpaceUserDtoRequest(chatSpaceCreatorUuid);
        String request = this.objectMapper.writeValueAsString(createChatSpaceUserDtoRequest);

        String responseAsString = this.mockMvc.
            perform(MockMvcRequestBuilders
                .post("/api/v1/chat-space-service/chatspaces")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(MockMvcResultMatchers.status().isConflict())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.contentType))
            .andReturn().getResponse().getContentAsString();

        ChatSpaceDtoResponse chatSpaceDtoResponse = this.objectMapper.readValue(responseAsString, ChatSpaceDtoResponse.class);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CONFLICT.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).isEmpty();
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).isNotEmpty();
        ContentChatSpaceErrorResponse contentChatSpaceErrorResponse = chatSpaceDtoResponse.getContent().getError()[0];
        Assertions.assertThat(contentChatSpaceErrorResponse.getType().getMessage()).isEqualTo("Unable to create new chat space.");
    }

    @Test
    public void givenAnExistingChatSpaceUser_WhenARequestIsMadeToGetTheChatSpaceUser_thenTheChatSpaceUserIsReturned() throws Exception {
        String responseAsString = this.mockMvc.
            perform(MockMvcRequestBuilders
                .get("/api/v1/chat-space-service/chatspaces/{uuid}", UuidOfExistingChatSpaceCreator)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.contentType))
            .andReturn().getResponse().getContentAsString();
        ChatSpaceDtoResponse chatSpaceDtoResponse = this.objectMapper.readValue(responseAsString, ChatSpaceDtoResponse.class);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).isEmpty();
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).isNotEmpty();
        ContentChatSpaceSuccessResponse success = chatSpaceDtoResponse.getContent().getSuccess()[0];
        Object payload =  success.getPayload();
        responseAsString = this.objectMapper.writeValueAsString(payload);
        com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceUser chatSpaceUser = this.objectMapper.readValue(responseAsString, com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceUser.class);
        Assertions.assertThat(chatSpaceUser.getUuid()).isNotNull();
        Assertions.assertThat(chatSpaceUser.getChatSpace()).hasSize(15);
        List<UUID> uuidList = chatSpaceUser.getChatSpace().stream().map(chatSpace -> chatSpace.getUuid()).collect(
            Collectors.toList());
        Assertions.assertThat(uuidList).contains(UuidOfExistingChatSpace);
    }

    @Test
    public void givenAnExistingUserAndANewChatSpace_whenTheNewChatSpaceIsAddedToTheUser_thenTheExistingUserHasANewChatSpaceAndAHttpCreatedIsReturned() throws Exception{
        final String NEW_CHATSPACE_NAME = "New ChatSpace";
        CreateChatSpaceDtoRequest createChatSpaceDtoRequest = new CreateChatSpaceDtoRequest(NEW_CHATSPACE_NAME);
        String request = this.objectMapper.writeValueAsString(createChatSpaceDtoRequest);
        String responseAsString = this.mockMvc.
            perform(MockMvcRequestBuilders
                .post("/api/v1/chat-space-service/chatspaces/{uuid}", UuidOfExistingChatSpaceCreator)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.contentType))
            .andReturn().getResponse().getContentAsString();
        ChatSpaceDtoResponse chatSpaceDtoResponse = this.objectMapper.readValue(responseAsString, ChatSpaceDtoResponse.class);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).isEmpty();
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).isNotEmpty();
        ContentChatSpaceSuccessResponse success = chatSpaceDtoResponse.getContent().getSuccess()[0];
        Object payload =  success.getPayload();
        responseAsString = this.objectMapper.writeValueAsString(payload);
        com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpace chatSpace = this.objectMapper.readValue(responseAsString, com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpace.class);
        Assertions.assertThat(chatSpace.getName()).isEqualTo(NEW_CHATSPACE_NAME);
    }

    @Test
    public void givenAnExistingUserAndAExistingChatSpaceName_whenTheChatSpaceIsAddedToTheUser_thenTheChatSpaceIsNotAddedAndAHttpConflictIsReturned() throws Exception{
        CreateChatSpaceDtoRequest createChatSpaceDtoRequest = new CreateChatSpaceDtoRequest(ExistingChatSpaceName);
        String request = this.objectMapper.writeValueAsString(createChatSpaceDtoRequest);
        String responseAsString = this.mockMvc.
            perform(MockMvcRequestBuilders
                .post("/api/v1/chat-space-service/chatspaces/{uuid}", UuidOfExistingChatSpaceCreator)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(MockMvcResultMatchers.status().isConflict())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.contentType))
            .andReturn().getResponse().getContentAsString();
        ChatSpaceDtoResponse chatSpaceDtoResponse = this.objectMapper.readValue(responseAsString, ChatSpaceDtoResponse.class);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CONFLICT.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).hasSize(0);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).hasSize(1);
        ContentChatSpaceErrorResponse contentChatSpaceErrorResponse = chatSpaceDtoResponse.getContent().getError()[0];
        ErrorType type = contentChatSpaceErrorResponse.getType();
        Assertions.assertThat(type.getMessage()).isEqualTo("Unable to create new chat space.");
    }

    @Test
    public void givenAnExistingChatSpaceCreatorAndAExistingChatSpaceAndChatSpaceMember_whenAMessageIsAddedToTheChatSpace_thenTheMessageIsAddedSuccessfullyToTheChatSpace() throws Exception{
        final String MESSAGE = "THIS IS THE NEW MESSAGE";
        ChatSpaceMessageRequestDto chatSpaceMessageRequestDto = ChatSpaceMessageRequestDto
            .builder()
            .uuidOfMember(UuidOfChatSpaceMember)
            .message(MESSAGE)
            .build();

        String request = this.objectMapper.writeValueAsString(chatSpaceMessageRequestDto);
        String responseAsString = this.mockMvc.
            perform(MockMvcRequestBuilders
                .post("/api/v1/chat-space-service/users/{chatSpaceUserUuid}/chatspaces/{chatSpaceUuid}",
                    UuidOfExistingChatSpaceCreator,
                    UuidOfExistingChatSpace)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(ContentType.Application_Hal_Json.contentType))
            .andReturn().getResponse().getContentAsString();
        ChatSpaceDtoResponse chatSpaceDtoResponse = this.objectMapper.readValue(responseAsString, ChatSpaceDtoResponse.class);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(
            HttpStatus.CREATED.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).hasSize(0);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).hasSize(1);
        Object payload = chatSpaceDtoResponse.getContent().getSuccess()[0].getPayload();
        String responseAsJson = this.objectMapper.writeValueAsString(payload);
        NewMessage newMessage = this.objectMapper.readValue(responseAsJson, NewMessage.class);
        Assertions.assertThat(newMessage.getUuid()).isNotNull();
        Assertions.assertThat(newMessage.getCreatedTimestamp()).isNotNull();
    }

}
