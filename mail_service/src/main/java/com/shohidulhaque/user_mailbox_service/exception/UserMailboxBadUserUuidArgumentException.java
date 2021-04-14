package com.shohidulhaque.user_mailbox_service.exception;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailboxDtoRequest;
import com.shohidulhaque.my_people.common_model.exception.user_mailbox_service.UserMailboxServiceValidationException;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import java.util.Collection;
import javax.validation.ConstraintViolation;

public class UserMailboxBadUserUuidArgumentException extends UserMailboxServiceValidationException {

    String userId;

    public UserMailboxBadUserUuidArgumentException(
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserMailboxDtoRequest>> violations,
        UserMailboxDtoRequest userMailboxDtoRequest) {
        super(responseType, errorType, violations, userMailboxDtoRequest);
    }

    public UserMailboxBadUserUuidArgumentException(String message,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserMailboxDtoRequest>> violations,
        UserMailboxDtoRequest userMailboxDtoRequest) {
        super(message, responseType, errorType, violations, userMailboxDtoRequest);
    }

    public UserMailboxBadUserUuidArgumentException(String message, Throwable cause,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserMailboxDtoRequest>> violations,
        UserMailboxDtoRequest userMailboxDtoRequest) {
        super(message, cause, responseType, errorType, violations, userMailboxDtoRequest);
    }

    public UserMailboxBadUserUuidArgumentException(Throwable cause,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserMailboxDtoRequest>> violations,
        UserMailboxDtoRequest userMailboxDtoRequest) {
        super(cause, responseType, errorType, violations, userMailboxDtoRequest);
    }

    public UserMailboxBadUserUuidArgumentException(String message, Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserMailboxDtoRequest>> violations,
        UserMailboxDtoRequest userMailboxDtoRequest) {
        super(message, cause, enableSuppression, writableStackTrace, responseType, errorType,
            violations, userMailboxDtoRequest);
    }
}
