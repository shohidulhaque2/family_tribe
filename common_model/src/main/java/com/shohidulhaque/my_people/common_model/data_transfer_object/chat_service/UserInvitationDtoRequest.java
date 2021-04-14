package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Deprecated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInvitationDtoRequest {
    @NotNull
    UUID invitedUserUuid;
}
