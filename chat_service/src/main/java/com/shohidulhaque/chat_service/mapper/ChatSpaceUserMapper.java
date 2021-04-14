package com.shohidulhaque.chat_service.mapper;

import com.shohidulhaque.chat_service.data_model.ChatSpaceUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChatSpaceUserMapper {

    ChatSpaceUserMapper INSTANCE = Mappers
        .getMapper(ChatSpaceUserMapper.class);

   com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceUser to(ChatSpaceUser chatSpaceUser);

}
