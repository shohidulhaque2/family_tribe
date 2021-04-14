package com.shohidulhaque.user_profile_service.exception;

import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoRequest;
import com.shohidulhaque.my_people.common_model.exception.user_profile_service.UserProfileServiceValidationException;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import java.util.Collection;
import javax.validation.ConstraintViolation;

public class ProfileBadArgumentException extends UserProfileServiceValidationException {

    public ProfileBadArgumentException(
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UserProfileDtoRequest userProfileDtoRequest) {
        super(responseType, errorType, violations, userProfileDtoRequest);
    }

    public ProfileBadArgumentException(String message,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UserProfileDtoRequest userProfileDtoRequest) {
        super(message, responseType, errorType, violations, userProfileDtoRequest);
    }

    public ProfileBadArgumentException(String message, Throwable cause,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UserProfileDtoRequest userProfileDtoRequest) {
        super(message, cause, responseType, errorType, violations, userProfileDtoRequest);
    }

    public ProfileBadArgumentException(Throwable cause,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UserProfileDtoRequest userProfileDtoRequest) {
        super(cause, responseType, errorType, violations, userProfileDtoRequest);
    }

    public ProfileBadArgumentException(String message, Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UserProfileDtoRequest userProfileDtoRequest) {
        super(message, cause, enableSuppression, writableStackTrace, responseType, errorType,
            violations, userProfileDtoRequest);
    }
}
