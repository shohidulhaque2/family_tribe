package com.shohidulhaque.my_people.common_model.exception.user_storage_service;

import com.shohidulhaque.my_people.common_model.exception.ServiceException;

public class UserStorageServiceException extends ServiceException {

    public UserStorageServiceException() {
    }

    public UserStorageServiceException(String message) {
        super(message);
    }

    public UserStorageServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserStorageServiceException(Throwable cause) {
        super(cause);
    }

    public UserStorageServiceException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
