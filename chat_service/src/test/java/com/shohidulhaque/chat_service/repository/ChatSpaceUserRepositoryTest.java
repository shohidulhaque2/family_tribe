package com.shohidulhaque.chat_service.repository;

import com.shohidulhaque.chat_service.data_model.ChatSpace;
import com.shohidulhaque.chat_service.data_model.ChatSpaceUser;
import com.shohidulhaque.chat_service.data_model.UserInvitation;
import java.time.Clock;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ChatSpaceUserRepositoryTest {

    static final UUID UuidOfNewChatSpaceCreator = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcf");

    static final UUID UuidOfExistingChatSpaceCreator = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcc");
    static final UUID UuidOfInvitedPerson = UUID.fromString("555593c2-d736-42ce-9926-ec944b02cbcc");
    static final UUID UuidOfExistingInvitationForInvitedPerson = UUID.fromString("85f02cec-14e1-4fc8-8f64-f790fa8452bc");


    Clock clock;

    @BeforeEach
    void beforeEachTest(){
        this.clock = Clock.systemUTC();
    }

    @Autowired
    ChatSpaceUserRepository testObject;

    @Test
    public void givenANewUser_whenANewChatSpaceIsPersisted_thenItIsSavedToTheRepository(){
        ChatSpaceUser chatSpaceUser = ChatSpaceUser
                                            .builder()
                                            .creatorUuid(UuidOfNewChatSpaceCreator)
                                            .uuid(UUID.randomUUID())
                                            .build();
        testObject.save(chatSpaceUser);
    }

    @Test
    public void givenAExistingChatSpaceUser_whenANewChatSpaceIsPersisted_thenItIsSavedToTheRepository(){

        Optional<ChatSpaceUser> chatSpaceUserOptional = this.testObject.getChatSpaceUser(
            UuidOfExistingChatSpaceCreator);

        Assertions.assertThat(chatSpaceUserOptional).hasValueSatisfying( c -> {
            Assertions.assertThat(c.getCreatorUuid()).isEqualTo(UuidOfExistingChatSpaceCreator);
        });
    }

    @Test
    public void givenAExistingChatSpaceUser_whenARequestIsMadeToGeTheirChatSpaces_thenTheyAreReturned(){
        List<ChatSpace> chatSpaceList = this.testObject.getUsersChatSpaces(
            UuidOfExistingChatSpaceCreator);
        Assertions.assertThat(chatSpaceList).isNotEmpty();
    }

    @Test
    public void givenAExistingChatSpaceUserAndAChatSpace_whenARequestIsMadeToGeTheirThatChatSpace_thenItIsReturned(){
        Optional<ChatSpace> chatSpaceOptional = this.testObject.getChatSpace(
            UuidOfExistingChatSpaceCreator, "ayl");
        Assertions.assertThat(chatSpaceOptional).hasValueSatisfying( chatSpace -> {
            Assertions.assertThat(chatSpace.getName()).isEqualTo("ayl");
        });
    }

    @Test
    public void givenAnExistingUserInvitation_whenItIsRetrieved_itIsReturned(){
        Optional<UserInvitation>  userInvitationsOptional = this.testObject.getInvitation(
            UuidOfExistingChatSpaceCreator, UuidOfInvitedPerson,
            UuidOfExistingInvitationForInvitedPerson);
        UserInvitation userInvitation = userInvitationsOptional.get();
        Assertions.assertThat(userInvitation.getInvitedUser()).isEqualTo(UuidOfInvitedPerson);
        Assertions.assertThat(userInvitation.getUuid()).isEqualTo(
            UuidOfExistingInvitationForInvitedPerson);
    }

    @Test
    public void givenAChatSpaceUserUuidInviteeUuidAndInvitationUuid_whenTheInvitaionIsRequested_thenItIsReturned(){
        Optional<UserInvitation>  userInvitationsOptional  = this.testObject
            .getInvitation(UuidOfExistingChatSpaceCreator, UuidOfInvitedPerson,
                UuidOfExistingInvitationForInvitedPerson);
        UserInvitation userInvitation = userInvitationsOptional.get();
        Assertions.assertThat(userInvitation.getUuid()).isEqualTo(UuidOfExistingInvitationForInvitedPerson);
        Assertions.assertThat(userInvitation.getInvitedUser()).isEqualTo(UuidOfInvitedPerson);
    }

}
