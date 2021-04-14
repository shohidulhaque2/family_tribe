package com.shohidulhaque.chat_service.data_transfer_object.validation;

import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceDtoResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ContentChatSpaceErrorResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ContentChatSpaceErrorResponse.ContentChatSpaceErrorResponseBuilder;
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

public class ChatSpaceDtoArgumentViolationResponse {
    //==============================================================================================
    static final int DEFAULT_AUTO_GROW_COLLECTION_LIMIT = 256;
    static final boolean IS_AUTO_GROW_NESTED_PATHS = true;
    //==============================================================================================
    public static ChatSpaceDtoResponse BadArgumentException(
        ChatSpaceDtoRequest chatSpaceDtoRequest,
        Collection<ConstraintViolation<ChatSpaceDtoRequest>> violations,
        ErrorType errorType
    ){
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(chatSpaceDtoRequest,
            "target", IS_AUTO_GROW_NESTED_PATHS, DEFAULT_AUTO_GROW_COLLECTION_LIMIT);
        List<List<FieldErrorResponse>> errorResponse = ProcessBindingResultToErrorResonse
            .ErrorResponse(bindingResult, violations);
        ChatSpaceDtoResponse chatSpaceDtoResponse = CreateUserBadArgumentExceptionResponse(errorResponse, errorType);
        final ResponseType responseType = new ResponseType(HttpStatus.BAD_REQUEST.toString(),
            HttpStatus.BAD_REQUEST.value(),
            Undefined.undefined.subCode);
        chatSpaceDtoResponse.getContent().setResponseType(responseType);
        return chatSpaceDtoResponse;
    }
    //==============================================================================================
    static private List<ContentChatSpaceErrorResponse> GetErrorResponseList(List<List<FieldErrorResponse>> errorResponseList, ErrorType errorType){
        final List<ContentChatSpaceErrorResponse> errorResponses = new LinkedList<>();
        ContentChatSpaceErrorResponseBuilder contentChatSpaceErrorResponseBuilder = ContentChatSpaceErrorResponse
            .builder();
        errorResponseList.stream().filter( x -> x.size() > 0).findFirst().ifPresent(fieldErrorResponseList -> {
            contentChatSpaceErrorResponseBuilder.payload(ErrorResponseToArray(fieldErrorResponseList));
            contentChatSpaceErrorResponseBuilder.type(errorType);
            errorResponses.add(contentChatSpaceErrorResponseBuilder.build());
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
    private static ChatSpaceDtoResponse CreateUserBadArgumentExceptionResponse(List<List<FieldErrorResponse>> errorResponseList, ErrorType errorType) {
        List<ContentChatSpaceErrorResponse> contentChatSpaceErrorResponse = GetErrorResponseList(errorResponseList, errorType);
        ResponseType responseType = new ResponseType(
            HttpStatus.BAD_REQUEST.toString(),
            HttpStatus.BAD_REQUEST.value(),
            Undefined.undefined.getSubCode());
        ChatSpaceDtoResponse chatSpaceDtoResponse = ChatSpaceDtoResponse.ErrorChatSpaceDtoResponse(responseType, contentChatSpaceErrorResponse);
        return chatSpaceDtoResponse;
    }
    //==============================================================================================
    public static ChatSpaceDtoResponse
    GetResponseBodyForError(ResponseType responseType, ErrorType errorType){
        ChatSpaceDtoResponse chatSpaceDtoResponse = new ChatSpaceDtoResponse();
        ContentChatSpaceErrorResponse contentChatSpaceErrorResponse = new ContentChatSpaceErrorResponse();
        contentChatSpaceErrorResponse.setType(errorType);
        chatSpaceDtoResponse.getContent().setResponseType(responseType);
        AddError(chatSpaceDtoResponse, contentChatSpaceErrorResponse);
        return chatSpaceDtoResponse;
    }
    private static void AddError(
        ChatSpaceDtoResponse chatSpaceDtoResponse,
        ContentChatSpaceErrorResponse contentChatSpaceErrorResponse) {
        ContentChatSpaceErrorResponse [] errorArray = chatSpaceDtoResponse.getContent().getError();
        final int NewArrayLength = errorArray.length + 1;
        ContentChatSpaceErrorResponse [] newErrorArray = Arrays
            .copyOf(errorArray, NewArrayLength);
        newErrorArray[newErrorArray.length - 1] = contentChatSpaceErrorResponse;
        chatSpaceDtoResponse.getContent().setError(newErrorArray);
    }
    //==============================================================================================

}
