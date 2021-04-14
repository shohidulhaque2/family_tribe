package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@ToString
@EqualsAndHashCode
public class MemberAcceptance extends ResponsePayload {
    UUID uuid;
    UUID memberUuid;
    UUID chatSpaceUuid;
}
