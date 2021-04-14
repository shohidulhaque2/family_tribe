package com.shohidulhaque.user_mailbox_service.exception;

import com.shohidulhaque.my_people.common_model.exception.user_mailbox_service.UserMailboxServiceException;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;

public class UserMailboxDoesNotExistException extends UserMailboxServiceException {

    public UserMailboxDoesNotExistException(
        ResponseType responseType,
        ErrorType errorType) {
        super(responseType, errorType);
    }

    public UserMailboxDoesNotExistException(String message,
        ResponseType responseType,
        ErrorType errorType) {
        super(message, responseType, errorType);
    }

    public UserMailboxDoesNotExistException(String message, Throwable cause,
        ResponseType responseType,
        ErrorType errorType) {
        super(message, cause, responseType, errorType);
    }

    public UserMailboxDoesNotExistException(Throwable cause,
        ResponseType responseType,
        ErrorType errorType) {
        super(cause, responseType, errorType);
    }

    public UserMailboxDoesNotExistException(String message, Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace,
        ResponseType responseType,
        ErrorType errorType) {
        super(message, cause, enableSuppression, writableStackTrace, responseType, errorType);
    }
}
