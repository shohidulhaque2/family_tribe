package com.shohidulhaque.user_mailbox_service.mapper;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.CreateUserMailboxDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.FromUserMailDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMail;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailbox;
import com.shohidulhaque.user_mailbox_service.data_model.InvitationMail;
import com.shohidulhaque.user_mailbox_service.data_model.Mail;
import com.shohidulhaque.user_mailbox_service.data_model.Mailbox;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMailboxMapper {

    UserMailboxMapper INSTANCE = Mappers
        .getMapper(UserMailboxMapper.class);
    UserMailbox userMailboxEntityToUserMailbox(Mailbox mailboxEntity);
    Mailbox createUserMailboxDtoRequestToUserMailboxEntity(
        CreateUserMailboxDtoRequest userMailboxDtoRequest);
    InvitationMail mailRequestToMailEntity(FromUserMailDtoRequest fromUserMailDtoRequest);
    UserMail entityMailToMail(Mail mail);
}
