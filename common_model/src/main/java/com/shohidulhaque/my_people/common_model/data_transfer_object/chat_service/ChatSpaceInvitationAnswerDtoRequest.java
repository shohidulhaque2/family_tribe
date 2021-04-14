package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatSpaceInvitationAnswerDtoRequest extends ChatSpaceDtoRequest{
    boolean isInvitationAccepted;
    String invitationId;
    String inviterId;
}
