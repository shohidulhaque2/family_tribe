package com.shohidulhaque.user_profile_service.exception;

import com.shohidulhaque.my_people.common_model.exception.user_profile_service.UserProfileServiceException;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;

public class UserDeletionException extends UserProfileServiceException {

    public UserDeletionException(
        ResponseType responseType,
        ErrorType errorType) {
        super(responseType, errorType);
    }

    public UserDeletionException(String message,
        ResponseType responseType,
        ErrorType errorType) {
        super(message, responseType, errorType);
    }

    public UserDeletionException(String message, Throwable cause,
        ResponseType responseType,
        ErrorType errorType) {
        super(message, cause, responseType, errorType);
    }

    public UserDeletionException(Throwable cause,
        ResponseType responseType,
        ErrorType errorType) {
        super(cause, responseType, errorType);
    }

    public UserDeletionException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace,
        ResponseType responseType,
        ErrorType errorType) {
        super(message, cause, enableSuppression, writableStackTrace, responseType, errorType);
    }
}
