package com.shohidulhaque.chat_service.mapper;

import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessagesMapper {

    MessagesMapper INSTANCE = Mappers
        .getMapper(MessagesMapper.class);

    Message to(com.shohidulhaque.chat_service.data_model.Message message);
}
