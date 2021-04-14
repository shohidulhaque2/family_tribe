package com.shohidulhaque.user_registry_service.dto_validation;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.ContentPersonRegistrationErrorResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.ContentPersonRegistrationErrorResponse.ContentPersonRegistrationErrorResponseBuilder;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.PersonRegistrationDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.PersonRegistrationDtoResponse;
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

public class PersonRegistrationDtoArgumentViolationResponse {

    static final int DEFAULT_AUTO_GROW_COLLECTION_LIMIT = 256;
    static final boolean IS_AUTO_GROW_NESTED_PATHS = true;
    public static PersonRegistrationDtoResponse BadArgumentException(
        PersonRegistrationDtoRequest personRegistrationDtoRequest,
        Collection<ConstraintViolation<PersonRegistrationDtoRequest>> violations,
        ErrorType errorType){
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(personRegistrationDtoRequest,
            "target", IS_AUTO_GROW_NESTED_PATHS, DEFAULT_AUTO_GROW_COLLECTION_LIMIT);
        List<List<FieldErrorResponse>> errorResponse = ProcessBindingResultToErrorResonse
            .ErrorResponse(bindingResult, violations);
        PersonRegistrationDtoResponse personRegistrationDtoResponse = createUserBadArgumentExceptionResponse(errorResponse, errorType);
        return personRegistrationDtoResponse;
    }
    static private PersonRegistrationDtoResponse createUserBadArgumentExceptionResponse(List<List<FieldErrorResponse>> errorResponseList, ErrorType errorType) {
        List<ContentPersonRegistrationErrorResponse> contentPersonRegistrationErrorResponse = getErrorResponseList(errorResponseList, errorType);
        ResponseType responseType = new ResponseType(
            HttpStatus.BAD_REQUEST.toString(),
            HttpStatus.BAD_REQUEST.value(),
            Undefined.undefined.getSubCode());
        PersonRegistrationDtoResponse personRegistrationDtoResponse = PersonRegistrationDtoResponse.ErrorUserProfileDtoResponse(responseType, contentPersonRegistrationErrorResponse);
        return personRegistrationDtoResponse;
    }
    static private List<ContentPersonRegistrationErrorResponse> getErrorResponseList(List<List<FieldErrorResponse>> errorResponseList, ErrorType errorType){
        final List<ContentPersonRegistrationErrorResponse> errorResponses = new LinkedList<>();
        ContentPersonRegistrationErrorResponseBuilder contentPersonRegistrationErrorResponseBuilder = ContentPersonRegistrationErrorResponse
            .builder();
        errorResponseList.stream().filter( x -> x.size() > 0).findFirst().ifPresent(fieldErrorResponseList -> {
            contentPersonRegistrationErrorResponseBuilder.payload(ErrorResponseToArray(fieldErrorResponseList));
            contentPersonRegistrationErrorResponseBuilder.type(errorType);
            errorResponses.add(contentPersonRegistrationErrorResponseBuilder.build());
        });
        return errorResponses;
    }
    //TODO:MOVE TO SHARED LIBRARY
    static private FieldErrorResponse []  ErrorResponseToArray(List<FieldErrorResponse> fieldErrorResponseList) {
        FieldErrorResponse [] fieldErrorResponseArray = new FieldErrorResponse[fieldErrorResponseList
            .size()];
        fieldErrorResponseList.toArray(fieldErrorResponseArray);
        return fieldErrorResponseArray;
    }
    //==============================================================================================
    public static PersonRegistrationDtoResponse
    GetResponseBodyForError(ResponseType responseType, ErrorType errorType){
        PersonRegistrationDtoResponse personRegistrationDtoResponse = new PersonRegistrationDtoResponse();
        ContentPersonRegistrationErrorResponse contentPersonRegistrationErrorResponse = new ContentPersonRegistrationErrorResponse();
        contentPersonRegistrationErrorResponse.setType(errorType);
        personRegistrationDtoResponse.getContent().setResponseType(responseType);
        AddError(personRegistrationDtoResponse, contentPersonRegistrationErrorResponse);
        return personRegistrationDtoResponse;
    }
    private static void AddError(
        PersonRegistrationDtoResponse personRegistrationDtoResponse,
        ContentPersonRegistrationErrorResponse CcontentPersonRegistrationErrorResponse) {
        ContentPersonRegistrationErrorResponse [] errorArray = personRegistrationDtoResponse.getContent().getError();
        final int NewArrayLength = errorArray.length + 1;
        ContentPersonRegistrationErrorResponse [] newErrorArray = Arrays.copyOf(errorArray, NewArrayLength);
        newErrorArray[newErrorArray.length - 1] = CcontentPersonRegistrationErrorResponse;
        personRegistrationDtoResponse.getContent().setError(newErrorArray);
    }//=============================================================================================
}
