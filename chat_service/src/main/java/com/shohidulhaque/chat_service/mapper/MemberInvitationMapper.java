package com.shohidulhaque.chat_service.mapper;

import com.shohidulhaque.chat_service.data_model.Member;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.MemberAcceptance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberInvitationMapper {

    MemberInvitationMapper INSTANCE = Mappers
        .getMapper(MemberInvitationMapper.class);

    @Mapping(ignore = true, target = "chatSpaceUuid")
    MemberAcceptance to(Member member);

}
