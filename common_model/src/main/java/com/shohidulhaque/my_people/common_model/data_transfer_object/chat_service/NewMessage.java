package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
public class NewMessage extends ResponsePayload {

    UUID uuid;

    Instant createdTimestamp;
}
