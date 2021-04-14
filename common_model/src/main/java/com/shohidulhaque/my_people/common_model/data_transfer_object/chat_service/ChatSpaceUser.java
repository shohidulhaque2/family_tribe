package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class ChatSpaceUser extends ResponsePayload{

    UUID uuid;

    @Default
    List<ChatSpace> chatSpace = new LinkedList<>();
}
