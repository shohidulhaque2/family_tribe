package com.shohidulhaque.my_people.common_model.exception.user_registry_service;

import com.shohidulhaque.my_people.common_model.exception.ServiceException;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UserRegistryServiceException extends ServiceException {

    ResponseType responseType;
    ErrorType errorType;

    public UserRegistryServiceException(
        ResponseType responseType,
        ErrorType errorType) {
        this.responseType = responseType;
        this.errorType = errorType;
    }

    public UserRegistryServiceException(String message,
        ResponseType responseType,
        ErrorType errorType) {
        super(message);
        this.responseType = responseType;
        this.errorType = errorType;
    }

    public UserRegistryServiceException(String message, Throwable cause,
        ResponseType responseType,
        ErrorType errorType) {
        super(message, cause);
        this.responseType = responseType;
        this.errorType = errorType;
    }

    public UserRegistryServiceException(Throwable cause,
        ResponseType responseType,
        ErrorType errorType) {
        super(cause);
        this.responseType = responseType;
        this.errorType = errorType;
    }

    public UserRegistryServiceException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace,
        ResponseType responseType,
        ErrorType errorType) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.responseType = responseType;
        this.errorType = errorType;
    }
}
