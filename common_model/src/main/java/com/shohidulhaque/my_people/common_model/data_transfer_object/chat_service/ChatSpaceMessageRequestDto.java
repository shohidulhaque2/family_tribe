package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ChatSpaceMessageRequestDto extends ChatSpaceDtoRequest {

    @NotNull
    UUID uuidOfMember;

    @NotBlank
    String message;

}
