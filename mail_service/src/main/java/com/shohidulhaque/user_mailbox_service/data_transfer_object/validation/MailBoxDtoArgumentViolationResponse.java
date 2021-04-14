package com.shohidulhaque.user_mailbox_service.data_transfer_object.validation;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.ContentUserMailboxErrorResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.ContentUserMailboxErrorResponse.ContentUserMailboxErrorResponseBuilder;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailboxDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailboxDtoResponse;
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

public class MailBoxDtoArgumentViolationResponse {

    //==============================================================================================
    static final int DEFAULT_AUTO_GROW_COLLECTION_LIMIT = 256;
    static final boolean IS_AUTO_GROW_NESTED_PATHS = true;
    //==============================================================================================
    public static UserMailboxDtoResponse BadArgumentException(
        UserMailboxDtoRequest userMailboxDtoRequest,
        Collection<ConstraintViolation<UserMailboxDtoRequest>> violations,
        ErrorType errorType
    ){
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(userMailboxDtoRequest,
            "target", IS_AUTO_GROW_NESTED_PATHS, DEFAULT_AUTO_GROW_COLLECTION_LIMIT);
        List<List<FieldErrorResponse>> errorResponse = ProcessBindingResultToErrorResonse
            .ErrorResponse(bindingResult, violations);
        UserMailboxDtoResponse userMailboxDtoResponse = createUserBadArgumentExceptionResponse(errorResponse, errorType);
        final ResponseType responseType = new ResponseType(HttpStatus.BAD_REQUEST.toString(),
            HttpStatus.BAD_REQUEST.value(),
            Undefined.undefined.subCode);
        userMailboxDtoResponse.getContent().setResponseType(responseType);
        return userMailboxDtoResponse;
    }
    //==============================================================================================
//    public static UserMailboxDtoResponse BadArgumentException(
//        UserMailboxUDtoRequest userMailboxDtoRequest,
//        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
//        ErrorType errorType
//    ){
//        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(userMailboxDtoRequest,
//            "target", IS_AUTO_GROW_NESTED_PATHS, DEFAULT_AUTO_GROW_COLLECTION_LIMIT);
//        List<List<FieldErrorResponse>> errorResponse = ProcessBindingResultToErrorResonse
//            .ErrorResponse(bindingResult, violations);
//        UserMailboxDtoResponse userMailboxDtoResponse = createUserBadArgumentExceptionResponse(errorResponse, errorType);
//        final ResponseType responseType = new ResponseType(HttpStatus.BAD_REQUEST.toString(),
//            HttpStatus.BAD_REQUEST.value(),
//            Undefined.undefined.subCode);
//        userMailboxDtoResponse.getContent().setResponseType(responseType);
//        return userMailboxDtoResponse;
//    }
    //==============================================================================================
    static private List<ContentUserMailboxErrorResponse> getErrorResponseList(List<List<FieldErrorResponse>> errorResponseList, ErrorType errorType){
        final List<ContentUserMailboxErrorResponse> errorResponses = new LinkedList<>();
        ContentUserMailboxErrorResponseBuilder contentUserMailboxErrorResponseBuilder = ContentUserMailboxErrorResponse
            .builder();
        errorResponseList.stream().filter( x -> x.size() > 0).findFirst().ifPresent(fieldErrorResponseList -> {
            contentUserMailboxErrorResponseBuilder.payload(ErrorResponseToArray(fieldErrorResponseList));
            contentUserMailboxErrorResponseBuilder.type(errorType);
            errorResponses.add(contentUserMailboxErrorResponseBuilder.build());
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
    private static UserMailboxDtoResponse createUserBadArgumentExceptionResponse(List<List<FieldErrorResponse>> errorResponseList, ErrorType errorType) {
        List<ContentUserMailboxErrorResponse> contentUserMailboxErrorResponseList = getErrorResponseList(errorResponseList, errorType);
        ResponseType responseType = new ResponseType(
            HttpStatus.BAD_REQUEST.toString(),
            HttpStatus.BAD_REQUEST.value(),
            Undefined.undefined.getSubCode());
        UserMailboxDtoResponse userMailboxDtoResponse = UserMailboxDtoResponse.ErrorUserProfileDtoResponse(responseType, contentUserMailboxErrorResponseList);
        return userMailboxDtoResponse;
    }
    //==============================================================================================
    public static UserMailboxDtoResponse
    GetResponseBodyForError(ResponseType responseType, ErrorType errorType){
        UserMailboxDtoResponse userMailboxDtoResponse = new UserMailboxDtoResponse();
        ContentUserMailboxErrorResponse contentUserMailboxErrorResponse = new ContentUserMailboxErrorResponse();
        contentUserMailboxErrorResponse.setType(errorType);
        userMailboxDtoResponse.getContent().setResponseType(responseType);
        AddError(userMailboxDtoResponse, contentUserMailboxErrorResponse);
        return userMailboxDtoResponse;
    }
    private static void AddError(
        UserMailboxDtoResponse userMailboxDtoResponse,
        ContentUserMailboxErrorResponse contentUserMailboxErrorResponse) {
        ContentUserMailboxErrorResponse [] errorArray = userMailboxDtoResponse.getContent().getError();
        final int NewArrayLength = errorArray.length + 1;
        ContentUserMailboxErrorResponse [] newErrorArray = Arrays
            .copyOf(errorArray, NewArrayLength);
        newErrorArray[newErrorArray.length - 1] = contentUserMailboxErrorResponse;
        userMailboxDtoResponse.getContent().setError(newErrorArray);
    }
    //==============================================================================================


}
