package com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service;

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
public class UserProfileDtoResponse extends RepresentationModel<UserProfileDtoResponse> implements
    Serializable {

    private static final String Type = UserProfileDtoResponse.class.getSimpleName();

    @Default
    ContentUserProfile content = new ContentUserProfile();

    public static UserProfileDtoResponse ErrorUserProfileDtoResponse(
        ResponseType responseType,
        List<ContentUserProfileErrorResponse> contentUserProfileErrorResponseLists
    ) {
        UserProfileDtoResponse userProfileDtoResponse = new UserProfileDtoResponse();
        userProfileDtoResponse.getContent().setResponseType(responseType);
        ContentUserProfileErrorResponse [] contentUserProfileErrorResponseArray = new ContentUserProfileErrorResponse[contentUserProfileErrorResponseLists.size()];
        contentUserProfileErrorResponseLists.toArray(contentUserProfileErrorResponseArray);
        userProfileDtoResponse.getContent().setError(contentUserProfileErrorResponseArray);
        return userProfileDtoResponse;
    }

    public static UserProfileDtoResponse SuccessUserProfileDtoResponse(
        ResponseType responseType,
        List <ContentUserProfileSuccessResponse> contentUserProfileSuccessResponseList
    ) {
        UserProfileDtoResponse userProfileDtoResponse = new UserProfileDtoResponse();
        userProfileDtoResponse.getContent().setResponseType(responseType);
        ContentUserProfileSuccessResponse [] contentUserProfileSuccessResponseArray = new ContentUserProfileSuccessResponse [contentUserProfileSuccessResponseList.size()];
        contentUserProfileSuccessResponseList.toArray(contentUserProfileSuccessResponseArray);
        userProfileDtoResponse.getContent().setSuccess(contentUserProfileSuccessResponseArray);
        return userProfileDtoResponse;
    }



//    public static UserProfileDtoResponse ErrorUserProfileDtoResponse(
//        ResponseType responseType,
//        ContentUserProfileErrorResponse [] contentUserProfileErrorResponseLists
//    ) {
//        UserProfileDtoResponse userProfileDtoResponse = new UserProfileDtoResponse();
//        userProfileDtoResponse.getContent().setResponseType(responseType);
//        userProfileDtoResponse.getContent().setError(contentUserProfileErrorResponseLists);
//        return userProfileDtoResponse;
//    }
//
//    public static UserProfileDtoResponse SuccessUserProfileDtoResponse(
//        ResponseType responseType,
//        ContentUserProfileSuccessResponse [] contentUserProfileSuccessResponseList
//    ) {
//        UserProfileDtoResponse userProfileDtoResponse = new UserProfileDtoResponse();
//        userProfileDtoResponse.getContent().setResponseType(responseType);
//        userProfileDtoResponse.getContent().setSuccess(contentUserProfileSuccessResponseList);
//        return userProfileDtoResponse;
//    }

}
