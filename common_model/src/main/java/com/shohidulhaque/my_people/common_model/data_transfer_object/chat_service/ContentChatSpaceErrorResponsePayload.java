package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorResponse;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ContentChatSpaceErrorResponsePayload implements Serializable {
    List<ErrorResponse> error;
}
