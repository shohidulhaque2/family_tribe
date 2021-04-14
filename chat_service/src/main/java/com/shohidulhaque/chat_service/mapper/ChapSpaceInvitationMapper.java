package com.shohidulhaque.chat_service.mapper;

import com.shohidulhaque.chat_service.data_model.UserInvitation;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceInvitation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChapSpaceInvitationMapper {

    ChapSpaceInvitationMapper INSTANCE = Mappers
        .getMapper(ChapSpaceInvitationMapper.class);

    ChatSpaceInvitation to(UserInvitation userInvitation);

}
