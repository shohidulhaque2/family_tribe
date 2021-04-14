package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import java.io.Serializable;
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
@JsonInclude(Include.NON_NULL)
public class ContentChatSpace implements Serializable {
    ResponseType responseType = new ResponseType("", 0, 0);
    ContentChatSpaceErrorResponse [] error = {};
    ContentChatSpaceSuccessResponse [] success = {};
}
