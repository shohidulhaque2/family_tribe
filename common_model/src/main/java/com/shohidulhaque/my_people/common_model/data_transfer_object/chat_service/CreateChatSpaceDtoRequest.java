package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import javax.validation.constraints.NotBlank;
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
public class CreateChatSpaceDtoRequest extends ChatSpaceDtoRequest{

    @NotBlank
    String chatSpaceName;

}
