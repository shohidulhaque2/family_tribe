package com.shohidulhaque.user_profile_service.exception;

import com.shohidulhaque.my_people.common_model.exception.user_profile_service.UserProfileServiceException;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;

public class ProfileBadUserProfileIdArgumentException extends UserProfileServiceException {

    String userId;
    String profileId;

    public ProfileBadUserProfileIdArgumentException(
        ResponseType responseType,
        ErrorType errorType, String userId, String profileId) {
        super(responseType, errorType);
        this.userId = userId;
        this.profileId = profileId;
    }

    public ProfileBadUserProfileIdArgumentException(String message,
        ResponseType responseType,
        ErrorType errorType, String userId, String profileId) {
        super(message, responseType, errorType);
        this.userId = userId;
        this.profileId = profileId;
    }

    public ProfileBadUserProfileIdArgumentException(String message, Throwable cause,
        ResponseType responseType,
        ErrorType errorType, String userId, String profileId) {
        super(message, cause, responseType, errorType);
        this.userId = userId;
        this.profileId = profileId;
    }

    public ProfileBadUserProfileIdArgumentException(Throwable cause,
        ResponseType responseType,
        ErrorType errorType, String userId, String profileId) {
        super(cause, responseType, errorType);
        this.userId = userId;
        this.profileId = profileId;
    }

    public ProfileBadUserProfileIdArgumentException(String message, Throwable cause,
        boolean enableSuppression, boolean writableStackTrace,
        ResponseType responseType,
        ErrorType errorType, String userId, String profileId) {
        super(message, cause, enableSuppression, writableStackTrace, responseType, errorType);
        this.userId = userId;
        this.profileId = profileId;
    }

}
