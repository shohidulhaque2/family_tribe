package com.shohidulhaque.chat_service.mapper;

import com.shohidulhaque.chat_service.data_model.Message;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessagesMapperTest {

    MessagesMapper testObject;

    @BeforeEach
    public void beforeTest(){
        this.testObject = MessagesMapper.INSTANCE;
    }

    @Test
    public void givenAMessagesDataModel_whenItIsMappedToAMessages_thenItIsMappedCorrect(){
        Message message = new Message();
        message.setId(1L);
        message.setVersion(1L);
        message.setMessage("Message");
        message.setCreatedTimestamp(Instant.now());

        com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.Message to = this.testObject
            .to(message);


    }
}
