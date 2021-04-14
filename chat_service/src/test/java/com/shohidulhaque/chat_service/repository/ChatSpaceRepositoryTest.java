package com.shohidulhaque.chat_service.repository;

import com.shohidulhaque.chat_service.data_model.ChatSpace;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ChatSpaceRepositoryTest {

    final static UUID UUID_OF_EXISTING_CHAT_SPACE_CREATOR = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcc");
    final static String EXISTING_CHAT_SPACE_NAME = "ayl";
    final static UUID UUID_OF_EXISTING_CHAT_SPACE = UUID.fromString("6ae2995b-7c8e-41d0-95af-a76fb27834f3");
    final static UUID UUID_OF_MEMBER = UUID.fromString("555593c2-d736-42ce-9926-ec944b02cbcc");
    @Autowired
    ChatSpaceRepository testObject;

    @Test
    public void givenAExistingChatSpace_whenItIsRetrieved_thenItIsReturned(){
        Optional<ChatSpace> chatSpaceOptional = testObject.findByName(EXISTING_CHAT_SPACE_NAME);
        Assertions.assertThat(chatSpaceOptional).hasValueSatisfying(chatSpace -> {
            Assertions.assertThat(chatSpace.getName()).isEqualTo(EXISTING_CHAT_SPACE_NAME);
            Assertions.assertThat(chatSpace.getUuid()).isEqualTo(UUID_OF_EXISTING_CHAT_SPACE);
        });
    }


    @Test
    public void givenAExistingChatSpaceInvitation_whenItIsRetrieved_thenItIsReturned(){
        Optional<ChatSpace> chatSpaceOptional = testObject.findByName(EXISTING_CHAT_SPACE_NAME);
        Assertions.assertThat(chatSpaceOptional).hasValueSatisfying(chatSpace -> {
            Assertions.assertThat(chatSpace.getName()).isEqualTo(EXISTING_CHAT_SPACE_NAME);
            Assertions.assertThat(chatSpace.getUuid()).isEqualTo(UUID_OF_EXISTING_CHAT_SPACE);
        });
    }

    @Test
    public void givenAnExistingChatSpace_whenTheChatSpaceIsSearched_thenItIsReturned(){
        Optional<ChatSpace> chatSpaceOptional = this.testObject.findChatSpace(
            UUID_OF_EXISTING_CHAT_SPACE_CREATOR,
            UUID_OF_EXISTING_CHAT_SPACE,
            UUID_OF_MEMBER);
        Assertions.assertThat(chatSpaceOptional).hasValueSatisfying(chatSpace -> {
            Assertions.assertThat(chatSpace.getName()).isEqualTo(EXISTING_CHAT_SPACE_NAME);
            }
        );
    }

}
