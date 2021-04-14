package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ContentChatSpaceSuccessResponsePayload implements Serializable {

    @Default
    List<ChatSpace> chatSpace = new LinkedList<>();
}
