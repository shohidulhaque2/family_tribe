package com.shohidulhaque.chat_service.mapper;

import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpace;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChatSpaceDataModelToChatSpaceMapper {

    ChatSpaceDataModelToChatSpaceMapper INSTANCE = Mappers
        .getMapper(ChatSpaceDataModelToChatSpaceMapper.class);

   ChatSpace chatSpaceDataModelToChatSpace( com.shohidulhaque.chat_service.data_model.ChatSpace chatSpace);

}
