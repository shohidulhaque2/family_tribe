package com.shohidulhaque.user_registry_service.service.exception;

import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;

public class UserRegistryRegistryActivationDoesNotExistException extends
    UserRegistryActivationException {

  public UserRegistryRegistryActivationDoesNotExistException(
      ResponseType responseType,
      ErrorType errorType) {
    super(responseType, errorType);
  }

  public UserRegistryRegistryActivationDoesNotExistException(String message,
      ResponseType responseType,
      ErrorType errorType) {
    super(message, responseType, errorType);
  }

  public UserRegistryRegistryActivationDoesNotExistException(String message, Throwable cause,
      ResponseType responseType,
      ErrorType errorType) {
    super(message, cause, responseType, errorType);
  }

  public UserRegistryRegistryActivationDoesNotExistException(Throwable cause,
      ResponseType responseType,
      ErrorType errorType) {
    super(cause, responseType, errorType);
  }

  public UserRegistryRegistryActivationDoesNotExistException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace,
      ResponseType responseType,
      ErrorType errorType) {
    super(message, cause, enableSuppression, writableStackTrace, responseType, errorType);
  }
}
