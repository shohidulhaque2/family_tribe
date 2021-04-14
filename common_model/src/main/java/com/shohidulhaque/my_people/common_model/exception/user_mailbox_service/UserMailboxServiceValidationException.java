package com.shohidulhaque.my_people.common_model.exception.user_mailbox_service;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailboxDtoRequest;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import java.util.Collection;
import java.util.LinkedList;
import javax.validation.ConstraintViolation;

public class UserMailboxServiceValidationException extends UserMailboxServiceException {

    Collection<ConstraintViolation<UserMailboxDtoRequest>> violations = new LinkedList<>();
    UserMailboxDtoRequest userMailboxDtoRequest;

    public UserMailboxServiceValidationException(
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserMailboxDtoRequest>> violations,
        UserMailboxDtoRequest userMailboxDtoRequest) {
        super(responseType, errorType);
        this.violations = violations;
        this.userMailboxDtoRequest = userMailboxDtoRequest;
    }

    public UserMailboxServiceValidationException(String message,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserMailboxDtoRequest>> violations,
        UserMailboxDtoRequest userMailboxDtoRequest) {
        super(message, responseType, errorType);
        this.violations = violations;
        this.userMailboxDtoRequest = userMailboxDtoRequest;
    }

    public UserMailboxServiceValidationException(String message, Throwable cause,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserMailboxDtoRequest>> violations,
        UserMailboxDtoRequest userMailboxDtoRequest) {
        super(message, cause, responseType, errorType);
        this.violations = violations;
        this.userMailboxDtoRequest = userMailboxDtoRequest;
    }

    public UserMailboxServiceValidationException(Throwable cause,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserMailboxDtoRequest>> violations,
        UserMailboxDtoRequest userMailboxDtoRequest) {
        super(cause, responseType, errorType);
        this.violations = violations;
        this.userMailboxDtoRequest = userMailboxDtoRequest;
    }

    public UserMailboxServiceValidationException(String message, Throwable cause,
        boolean enableSuppression, boolean writableStackTrace,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserMailboxDtoRequest>> violations,
        UserMailboxDtoRequest userMailboxDtoRequest) {
        super(message, cause, enableSuppression, writableStackTrace, responseType, errorType);
        this.violations = violations;
        this.userMailboxDtoRequest = userMailboxDtoRequest;
    }
}
