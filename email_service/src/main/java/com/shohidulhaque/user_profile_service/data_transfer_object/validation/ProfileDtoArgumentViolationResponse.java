package com.shohidulhaque.user_profile_service.data_transfer_object.validation;

import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.ContentUserProfileErrorResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.ContentUserProfileErrorResponse.ContentUserProfileErrorResponseBuilder;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UpdateProfileDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoResponse;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.Undefined;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.FieldErrorResponse;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import com.shohidulhaque.my_people.common_utility.ProcessBindingResultToErrorResonse;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;

public class ProfileDtoArgumentViolationResponse {
    //==============================================================================================
    static final int DEFAULT_AUTO_GROW_COLLECTION_LIMIT = 256;
    static final boolean IS_AUTO_GROW_NESTED_PATHS = true;
    //==============================================================================================
    public static UserProfileDtoResponse BadArgumentException(
        UserProfileDtoRequest userProfileDtoRequest,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        ErrorType errorType
    ){
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(userProfileDtoRequest,
            "target", IS_AUTO_GROW_NESTED_PATHS, DEFAULT_AUTO_GROW_COLLECTION_LIMIT);
        List<List<FieldErrorResponse>> errorResponse = ProcessBindingResultToErrorResonse
            .ErrorResponse(bindingResult, violations);
        UserProfileDtoResponse userProfileDtoResponse = createUserBadArgumentExceptionResponse(errorResponse, errorType);
        final ResponseType responseType = new ResponseType(HttpStatus.BAD_REQUEST.toString(),
            HttpStatus.BAD_REQUEST.value(),
            Undefined.undefined.subCode);
        userProfileDtoResponse.getContent().setResponseType(responseType);
        return userProfileDtoResponse;
    }
    //==============================================================================================
    public static UserProfileDtoResponse BadArgumentException(
        UpdateProfileDtoRequest userProfileDtoRequest,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        ErrorType errorType
    ){
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(userProfileDtoRequest,
            "target", IS_AUTO_GROW_NESTED_PATHS, DEFAULT_AUTO_GROW_COLLECTION_LIMIT);
        List<List<FieldErrorResponse>> errorResponse = ProcessBindingResultToErrorResonse
            .ErrorResponse(bindingResult, violations);
        UserProfileDtoResponse userProfileDtoResponse = createUserBadArgumentExceptionResponse(errorResponse, errorType);
        final ResponseType responseType = new ResponseType(HttpStatus.BAD_REQUEST.toString(),
            HttpStatus.BAD_REQUEST.value(),
            Undefined.undefined.subCode);
        userProfileDtoResponse.getContent().setResponseType(responseType);
        return userProfileDtoResponse;
    }
    //==============================================================================================
    static private List<ContentUserProfileErrorResponse> getErrorResponseList(List<List<FieldErrorResponse>> errorResponseList, ErrorType errorType){
        final List<ContentUserProfileErrorResponse> errorResponses = new LinkedList<>();
        ContentUserProfileErrorResponseBuilder contentUserProfileErrorResponseBuilder = ContentUserProfileErrorResponse
            .builder();
        errorResponseList.stream().filter( x -> x.size() > 0).findFirst().ifPresent(fieldErrorResponseList -> {
            contentUserProfileErrorResponseBuilder.payload(ErrorResponseToArray(fieldErrorResponseList));
            contentUserProfileErrorResponseBuilder.type(errorType);
            errorResponses.add(contentUserProfileErrorResponseBuilder.build());
        });
        return errorResponses;
    }
    //==============================================================================================
    private static FieldErrorResponse []  ErrorResponseToArray(List<FieldErrorResponse> fieldErrorResponseList) {
        FieldErrorResponse [] fieldErrorResponseArray = new FieldErrorResponse[fieldErrorResponseList
            .size()];
        fieldErrorResponseList.toArray(fieldErrorResponseArray);
        return fieldErrorResponseArray;
    }
    //==============================================================================================
    private static UserProfileDtoResponse createUserBadArgumentExceptionResponse(List<List<FieldErrorResponse>> errorResponseList, ErrorType errorType) {
        List<ContentUserProfileErrorResponse> contentUserProfileErrorResponseList = getErrorResponseList(errorResponseList, errorType);
        ResponseType responseType = new ResponseType(
            HttpStatus.BAD_REQUEST.toString(),
            HttpStatus.BAD_REQUEST.value(),
            Undefined.undefined.getSubCode());
        UserProfileDtoResponse userProfileDtoResponse = UserProfileDtoResponse.ErrorUserProfileDtoResponse(responseType, contentUserProfileErrorResponseList);
        return userProfileDtoResponse;
    }
    //==============================================================================================
    public static UserProfileDtoResponse
    GetResponseBodyForError(ResponseType responseType, ErrorType errorType){
        UserProfileDtoResponse userProfileDtoResponse = new UserProfileDtoResponse();
        ContentUserProfileErrorResponse contentUserProfileErrorResponse = new ContentUserProfileErrorResponse();
        contentUserProfileErrorResponse.setType(errorType);
        userProfileDtoResponse.getContent().setResponseType(responseType);
        AddError(userProfileDtoResponse, contentUserProfileErrorResponse);
        return userProfileDtoResponse;
    }
    private static void AddError(
        UserProfileDtoResponse userProfileDtoResponse,
        ContentUserProfileErrorResponse contentPersonRegistrationErrorResponse) {
        ContentUserProfileErrorResponse [] errorArray = userProfileDtoResponse.getContent().getError();
        final int NewArrayLength = errorArray.length + 1;
        ContentUserProfileErrorResponse [] newErrorArray = Arrays
            .copyOf(errorArray, NewArrayLength);
        newErrorArray[newErrorArray.length - 1] = contentPersonRegistrationErrorResponse;
        userProfileDtoResponse.getContent().setError(newErrorArray);
    }
    //==============================================================================================
}
