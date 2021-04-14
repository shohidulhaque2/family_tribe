package com.shohidulhaque.user_profile_service.exception;

import com.shohidulhaque.my_people.common_model.exception.user_profile_service.UserProfileServiceException;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;

public class ProfileBadUserIdArgumentException extends  UserProfileServiceException {

    String userId;

    public ProfileBadUserIdArgumentException(
        ResponseType responseType,
        ErrorType errorType, String userId) {
        super(responseType, errorType);
        this.userId = userId;
    }

    public ProfileBadUserIdArgumentException(String message,
        ResponseType responseType,
        ErrorType errorType, String userId) {
        super(message, responseType, errorType);
        this.userId = userId;
    }

    public ProfileBadUserIdArgumentException(String message, Throwable cause,
        ResponseType responseType,
        ErrorType errorType, String userId) {
        super(message, cause, responseType, errorType);
        this.userId = userId;
    }

    public ProfileBadUserIdArgumentException(Throwable cause,
        ResponseType responseType,
        ErrorType errorType, String userId) {
        super(cause, responseType, errorType);
        this.userId = userId;
    }

    public ProfileBadUserIdArgumentException(String message, Throwable cause,
        boolean enableSuppression, boolean writableStackTrace,
        ResponseType responseType,
        ErrorType errorType, String userId) {
        super(message, cause, enableSuppression, writableStackTrace, responseType, errorType);
        this.userId = userId;
    }
}
