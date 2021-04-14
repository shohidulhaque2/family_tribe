package com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service;

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
public class PersonRegistrationDtoResponse extends RepresentationModel<PersonRegistrationDtoResponse> implements
    Serializable {

    private static final String Type = PersonRegistrationDtoResponse.class.getSimpleName();

    @Default
    protected ContentPersonRegistration content = new ContentPersonRegistration();

    public static PersonRegistrationDtoResponse ErrorUserProfileDtoResponse(
        ResponseType responseType,
        List<ContentPersonRegistrationErrorResponse> contentUserProfileErrorResponseLists
    ) {
        PersonRegistrationDtoResponse userProfileDtoResponse = new PersonRegistrationDtoResponse();
        userProfileDtoResponse.getContent().setResponseType(responseType);
        ContentPersonRegistrationErrorResponse[] contentUserProfileErrorResponseArray = new ContentPersonRegistrationErrorResponse[contentUserProfileErrorResponseLists.size()];
        contentUserProfileErrorResponseLists.toArray(contentUserProfileErrorResponseArray);
        userProfileDtoResponse.getContent().setError(contentUserProfileErrorResponseArray);
        return userProfileDtoResponse;
    }

    public static PersonRegistrationDtoResponse SuccessUserProfileDtoResponse(
        ResponseType responseType,
        List <ContentPersonRegistrationSuccessResponse> contentUserProfileSuccessResponseList
    ) {
        PersonRegistrationDtoResponse userProfileDtoResponse = new PersonRegistrationDtoResponse();
        userProfileDtoResponse.getContent().setResponseType(responseType);
        ContentPersonRegistrationSuccessResponse[] contentUserProfileSuccessResponseArray = new ContentPersonRegistrationSuccessResponse[contentUserProfileSuccessResponseList.size()];
        contentUserProfileSuccessResponseList.toArray(contentUserProfileSuccessResponseArray);
        userProfileDtoResponse.getContent().setSuccess(contentUserProfileSuccessResponseArray);
        return userProfileDtoResponse;
    }


}
