package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatSpaceDtoResponse extends RepresentationModel<ChatSpaceDtoResponse> implements Serializable {

    @Default
    String type = ChatSpaceDtoResponse.class.getSimpleName();

    @Default
    ContentChatSpace content = new ContentChatSpace();

    public static ChatSpaceDtoResponse ErrorChatSpaceDtoResponse(
        ResponseType responseType,
        List<ContentChatSpaceErrorResponse> contentChatSpaceErrorResponseLists
    ) {
        ChatSpaceDtoResponse chatSpaceDtoResponse = new ChatSpaceDtoResponse();
        chatSpaceDtoResponse.getContent().setResponseType(responseType);
        ContentChatSpaceErrorResponse [] contentChatSpaceErrorResponseArray = new ContentChatSpaceErrorResponse[contentChatSpaceErrorResponseLists.size()];
        contentChatSpaceErrorResponseLists.toArray(contentChatSpaceErrorResponseArray);
        chatSpaceDtoResponse.getContent().setError(contentChatSpaceErrorResponseArray);
        return chatSpaceDtoResponse;
    }

    public static ChatSpaceDtoResponse SuccessChatSpaceDtoResponse(
        ResponseType responseType,
        List<ContentChatSpaceSuccessResponse> contentChatSpaceSuccessResponseList
    ) {
        ChatSpaceDtoResponse chatSpaceDtoResponse = new ChatSpaceDtoResponse();
        chatSpaceDtoResponse.getContent().setResponseType(responseType);
        ContentChatSpaceSuccessResponse [] contentChatSpaceSuccessResponseArray = new ContentChatSpaceSuccessResponse [contentChatSpaceSuccessResponseList.size()];
        contentChatSpaceSuccessResponseList.toArray(contentChatSpaceSuccessResponseArray);
        chatSpaceDtoResponse.getContent().setSuccess(contentChatSpaceSuccessResponseArray);
        return chatSpaceDtoResponse;
    }

}
