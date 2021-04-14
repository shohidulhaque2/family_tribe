package com.shohidulhaque.my_people.common_model.exception.user_profile_service;

import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoRequest;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import java.util.Collection;
import java.util.LinkedList;
import javax.validation.ConstraintViolation;
import lombok.Data;

@Data
public class UserProfileServiceValidationException extends UserProfileServiceException {

    Collection<ConstraintViolation<UserProfileDtoRequest>> violations = new LinkedList<>();
    UserProfileDtoRequest userProfileDtoRequest;

    public UserProfileServiceValidationException(
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UserProfileDtoRequest userProfileDtoRequest) {
        super(responseType, errorType);
        this.violations = violations;
        this.userProfileDtoRequest = userProfileDtoRequest;
    }

    public UserProfileServiceValidationException(String message,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UserProfileDtoRequest userProfileDtoRequest) {
        super(message, responseType, errorType);
        this.violations = violations;
        this.userProfileDtoRequest = userProfileDtoRequest;
    }

    public UserProfileServiceValidationException(String message, Throwable cause,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UserProfileDtoRequest userProfileDtoRequest) {
        super(message, cause, responseType, errorType);
        this.violations = violations;
        this.userProfileDtoRequest = userProfileDtoRequest;
    }

    public UserProfileServiceValidationException(Throwable cause,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UserProfileDtoRequest userProfileDtoRequest) {
        super(cause, responseType, errorType);
        this.violations = violations;
        this.userProfileDtoRequest = userProfileDtoRequest;
    }

    public UserProfileServiceValidationException(String message, Throwable cause,
        boolean enableSuppression, boolean writableStackTrace,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UserProfileDtoRequest userProfileDtoRequest) {
        super(message, cause, enableSuppression, writableStackTrace, responseType, errorType);
        this.violations = violations;
        this.userProfileDtoRequest = userProfileDtoRequest;
    }
}
