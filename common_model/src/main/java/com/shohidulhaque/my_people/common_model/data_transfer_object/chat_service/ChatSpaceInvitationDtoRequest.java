package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public class ChatSpaceInvitationDtoRequest extends ChatSpaceDtoRequest {
    String email;
    String nickName;
//    UUID invitationToUuid;
    @NotNull
    UUID chatSpaceUuid;
}
