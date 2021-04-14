package com.shohidulhaque.chat_service.mapper;

import com.shohidulhaque.chat_service.data_model.Message;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.NewMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NewMessageMapper {

    NewMessageMapper INSTANCE = Mappers
        .getMapper(NewMessageMapper.class);

    NewMessage to(Message message);

}
