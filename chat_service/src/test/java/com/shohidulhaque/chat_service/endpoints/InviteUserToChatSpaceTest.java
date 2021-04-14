package com.shohidulhaque.chat_service.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shohidulhaque.chat_service.service.ServiceProxy;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceDtoResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceInvitation;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceInvitationDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.MemberAcceptance;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
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
@WebMvcTest(InviteUserToChatSpace.class)
public class InviteUserToChatSpaceTest {

    static UUID NewChatSpaceCreatorUuid = UUID.fromString("62337a2c-69d6-4861-9a59-f21eeb782554");

    static UUID ExistingChatSpaceCreatorUuid = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcc");

    static UUID ExistingChatSpaceCreatorUuid2 = UUID.fromString("555593c2-d736-42ce-9926-ec944b02cbcc");

    static UUID ExistingChatSpaceUuid = UUID.fromString("6ae2995b-7c8e-41d0-95af-a76fb27834f3");

    final static UUID UuidOfChatSpaceCreatedByAExistingChatSpaceCreator = UUID.fromString("6ae2995b-7c8e-41d0-95af-a76fb27834f3");

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ServiceProxy serviceProxy;

    @Test
    public void givenAnExistingUserAndAChatSpace_whenAPersonIsInvited_thenAnInviteMessageIsSentToThem()
        throws Exception{

        Mockito.when(this.serviceProxy.doesMemberExist("brother@brother.com")).thenReturn(Optional.of(UUID.randomUUID()));

        Mockito.when(serviceProxy.sendInvitationToUser(Mockito.eq(ExistingChatSpaceCreatorUuid2), Mockito.any())).thenReturn(true);

        ChatSpaceInvitationDtoRequest chatSpaceInvitationDtoRequest = new ChatSpaceInvitationDtoRequest();
        chatSpaceInvitationDtoRequest.setEmail("brother@brother.com");
        chatSpaceInvitationDtoRequest.setChatSpaceUuid(ExistingChatSpaceUuid);

        String request = this.objectMapper.writeValueAsString(chatSpaceInvitationDtoRequest);
        String responseAsJsonString = this.mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/chat-space-service/users/{uuid}/chatspaces/members", ExistingChatSpaceCreatorUuid)
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().contentType(UserChatSpaceEndPointsTest.ContentType.Application_Hal_Json.contentType))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn().getResponse().getContentAsString();

        ChatSpaceDtoResponse chatSpaceDtoResponse = this.objectMapper.readValue(responseAsJsonString, ChatSpaceDtoResponse.class);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).isEmpty();
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).isNotEmpty();
        Object payload =  chatSpaceDtoResponse.getContent().getSuccess()[0].getPayload();
        responseAsJsonString = this.objectMapper.writeValueAsString(payload);
        ChatSpaceInvitation ChatSpaceInvitation = this.objectMapper.readValue(responseAsJsonString, ChatSpaceInvitation.class);
        Mockito.verify(this.serviceProxy, Mockito.atMost(1)).doesMemberExist("brother@brother.com");
        Mockito.verify(this.serviceProxy, Mockito.atMost(1)).sendInvitationToUser(Mockito.eq(ExistingChatSpaceCreatorUuid2), Mockito.any());
    }


    @Test
    public void givenAnExistingUserAndAnInvite_whenTheInvitedPersonAcceptsTheInvitation_thenThePersonIsAddedAsAMember() throws Exception {
        UUID UuidOfInvitation = UUID.fromString("85f02cec-14e1-4fc8-8f64-f790fa8452bc");
        UUID UuidOfInvitedUser = UUID.fromString("555593c2-d736-42ce-9926-ec944b02cbcc");

        String responseAsJsonString = this.mockMvc.perform(
            MockMvcRequestBuilders.patch("/api/v1/chat-space-service/users/{chatSpaceUserUuid}/chatspaces/{chatSpaceUuid}/invitation/{invitationUuid}/person/{uuidOfInvitee}",
                ExistingChatSpaceCreatorUuid, UuidOfChatSpaceCreatedByAExistingChatSpaceCreator, UuidOfInvitation, UuidOfInvitedUser)
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().contentType(
                UserChatSpaceEndPointsTest.ContentType.Application_Hal_Json.contentType))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn().getResponse().getContentAsString();

        ChatSpaceDtoResponse chatSpaceDtoResponse = this.objectMapper.readValue(responseAsJsonString, ChatSpaceDtoResponse.class);
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getResponseType().getCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getError()).isEmpty();
        Assertions.assertThat(chatSpaceDtoResponse.getContent().getSuccess()).isNotEmpty();
        Object payload =  chatSpaceDtoResponse.getContent().getSuccess()[0].getPayload();
        responseAsJsonString = this.objectMapper.writeValueAsString(payload);
        MemberAcceptance memberAcceptance = this.objectMapper.readValue(responseAsJsonString, MemberAcceptance.class);
        Assertions.assertThat(memberAcceptance.getChatSpaceUuid()).isEqualTo(UuidOfChatSpaceCreatedByAExistingChatSpaceCreator);
        Assertions.assertThat(memberAcceptance.getMemberUuid()).isEqualTo(UuidOfInvitedUser);
    }


}
