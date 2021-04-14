package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateChatSpaceUserDtoRequest extends ChatSpaceDtoRequest{

    @NotNull
    UUID uuid;
}
