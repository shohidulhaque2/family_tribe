package com.shohidulhaque.user_registry_service.service.exception;

import com.shohidulhaque.my_people.common_model.exception.user_registry_service.UserRegistryServiceException;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;

public class UserRegistryUpdateServiceException extends UserRegistryServiceException {

    public UserRegistryUpdateServiceException(
        ResponseType responseType,
        ErrorType errorType) {
        super(responseType, errorType);
    }

    public UserRegistryUpdateServiceException(String message,
        ResponseType responseType,
        ErrorType errorType) {
        super(message, responseType, errorType);
    }

    public UserRegistryUpdateServiceException(String message, Throwable cause,
        ResponseType responseType,
        ErrorType errorType) {
        super(message, cause, responseType, errorType);
    }

    public UserRegistryUpdateServiceException(Throwable cause,
        ResponseType responseType,
        ErrorType errorType) {
        super(cause, responseType, errorType);
    }

    public UserRegistryUpdateServiceException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace,
        ResponseType responseType,
        ErrorType errorType) {
        super(message, cause, enableSuppression, writableStackTrace, responseType, errorType);
    }

}
