package com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service;

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
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserMailboxDtoResponse extends RepresentationModel<UserMailboxDtoResponse> implements
    Serializable {

    private static final String Type = UserMailboxDtoResponse.class.getSimpleName();

    @Default
    ContentMailbox content = new ContentMailbox();

    public static UserMailboxDtoResponse ErrorUserProfileDtoResponse(
        ResponseType responseType,
        List<ContentUserMailboxErrorResponse> contentUserMailboxErrorResponseLists
    ) {
        UserMailboxDtoResponse userMailBoxDtoResponse = new UserMailboxDtoResponse();
        userMailBoxDtoResponse.getContent().setResponseType(responseType);
        ContentUserMailboxErrorResponse[] contentUserMailboxErrorResponseArray = new ContentUserMailboxErrorResponse[contentUserMailboxErrorResponseLists
            .size()];
        contentUserMailboxErrorResponseLists.toArray(contentUserMailboxErrorResponseArray);
        userMailBoxDtoResponse.getContent().setError(contentUserMailboxErrorResponseArray);
        return userMailBoxDtoResponse;
    }

    public static UserMailboxDtoResponse SuccessUserProfileDtoResponse(
        ResponseType responseType,
        List <ContentUserMailboxSuccessResponse> contentUserMailboxSuccessResponseList
    ) {
        UserMailboxDtoResponse userMailBoxDtoResponse = new UserMailboxDtoResponse();
        userMailBoxDtoResponse.getContent().setResponseType(responseType);
        ContentUserMailboxSuccessResponse[] contentUserMailboxSuccessResponseArray = new ContentUserMailboxSuccessResponse[contentUserMailboxSuccessResponseList
            .size()];
        contentUserMailboxSuccessResponseList.toArray(contentUserMailboxSuccessResponseArray);
        userMailBoxDtoResponse.getContent().setSuccess(contentUserMailboxSuccessResponseArray);
        return userMailBoxDtoResponse;
    }

}
