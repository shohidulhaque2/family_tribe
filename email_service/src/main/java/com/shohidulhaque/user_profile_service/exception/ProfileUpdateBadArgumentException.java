package com.shohidulhaque.user_profile_service.exception;

import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UpdateProfileDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoRequest;
import com.shohidulhaque.my_people.common_model.exception.user_profile_service.UserProfileServiceValidationException;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import java.util.Collection;
import javax.validation.ConstraintViolation;

public class ProfileUpdateBadArgumentException extends UserProfileServiceValidationException {

    public ProfileUpdateBadArgumentException(
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UpdateProfileDtoRequest updateProfileDtoRequest) {
        super(responseType, errorType, violations, updateProfileDtoRequest);
    }

    public ProfileUpdateBadArgumentException(String message,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UpdateProfileDtoRequest updateProfileDtoRequest) {
        super(message, responseType, errorType, violations, updateProfileDtoRequest);
    }

    public ProfileUpdateBadArgumentException(String message, Throwable cause,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UpdateProfileDtoRequest updateProfileDtoRequest) {
        super(message, cause, responseType, errorType, violations, updateProfileDtoRequest);
    }

    public ProfileUpdateBadArgumentException(Throwable cause,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UpdateProfileDtoRequest updateProfileDtoRequest) {
        super(cause, responseType, errorType, violations, updateProfileDtoRequest);
    }

    public ProfileUpdateBadArgumentException(String message, Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<UserProfileDtoRequest>> violations,
        UpdateProfileDtoRequest updateProfileDtoRequest) {
        super(message, cause, enableSuppression, writableStackTrace, responseType, errorType,
            violations, updateProfileDtoRequest);
    }
}
