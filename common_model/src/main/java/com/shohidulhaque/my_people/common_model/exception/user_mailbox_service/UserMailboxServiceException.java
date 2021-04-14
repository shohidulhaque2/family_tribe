package com.shohidulhaque.my_people.common_model.exception.user_mailbox_service;

import com.shohidulhaque.my_people.common_model.exception.ServiceException;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import lombok.Data;

@Data
public class UserMailboxServiceException extends ServiceException {

    ResponseType responseType;
    ErrorType errorType;

    public UserMailboxServiceException(
        ResponseType responseType,
        ErrorType errorType) {
        this.responseType = responseType;
        this.errorType = errorType;
    }

    public UserMailboxServiceException(String message,
        ResponseType responseType,
        ErrorType errorType) {
        super(message);
        this.responseType = responseType;
        this.errorType = errorType;
    }

    public UserMailboxServiceException(String message, Throwable cause,
        ResponseType responseType,
        ErrorType errorType) {
        super(message, cause);
        this.responseType = responseType;
        this.errorType = errorType;
    }

    public UserMailboxServiceException(Throwable cause,
        ResponseType responseType,
        ErrorType errorType) {
        super(cause);
        this.responseType = responseType;
        this.errorType = errorType;
    }

    public UserMailboxServiceException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace,
        ResponseType responseType,
        ErrorType errorType) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.responseType = responseType;
        this.errorType = errorType;
    }
}
