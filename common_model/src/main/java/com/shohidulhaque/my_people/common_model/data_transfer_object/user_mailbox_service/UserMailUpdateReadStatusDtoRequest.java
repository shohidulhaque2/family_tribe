package com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service;

import javax.validation.constraints.NotNull;
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
public class UserMailUpdateReadStatusDtoRequest extends UserMailboxDtoRequest{

    @NotNull
    Boolean read;

}
