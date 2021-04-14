package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Data
@Builder
@JsonInclude(Include.NON_NULL)
@Deprecated
public class ChatSpaceInvitation extends ChatSpaceDtoRequest {
    UUID uuid;
}
