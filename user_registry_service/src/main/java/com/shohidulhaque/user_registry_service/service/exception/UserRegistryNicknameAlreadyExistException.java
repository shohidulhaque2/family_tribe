package com.shohidulhaque.user_registry_service.service.exception;


import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.PersonRegistrationDtoRequest;
import com.shohidulhaque.my_people.common_model.exception.user_registry_service.UserRegistryServiceValidationException;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import java.util.Collection;
import javax.validation.ConstraintViolation;

public class UserRegistryNicknameAlreadyExistException extends UserRegistryServiceValidationException {

  public UserRegistryNicknameAlreadyExistException(
      ResponseType responseType,
      ErrorType errorType,
      Collection<ConstraintViolation<PersonRegistrationDtoRequest>> violations,
      PersonRegistrationDtoRequest personRegistrationDtoRequest) {
    super(responseType, errorType, violations, personRegistrationDtoRequest);
  }

  public UserRegistryNicknameAlreadyExistException(String message,
      ResponseType responseType,
      ErrorType errorType,
      Collection<ConstraintViolation<PersonRegistrationDtoRequest>> violations,
      PersonRegistrationDtoRequest personRegistrationDtoRequest) {
    super(message, responseType, errorType, violations, personRegistrationDtoRequest);
  }

  public UserRegistryNicknameAlreadyExistException(String message, Throwable cause,
      ResponseType responseType,
      ErrorType errorType,
      Collection<ConstraintViolation<PersonRegistrationDtoRequest>> violations,
      PersonRegistrationDtoRequest personRegistrationDtoRequest) {
    super(message, cause, responseType, errorType, violations, personRegistrationDtoRequest);
  }

  public UserRegistryNicknameAlreadyExistException(Throwable cause,
      ResponseType responseType,
      ErrorType errorType,
      Collection<ConstraintViolation<PersonRegistrationDtoRequest>> violations,
      PersonRegistrationDtoRequest personRegistrationDtoRequest) {
    super(cause, responseType, errorType, violations, personRegistrationDtoRequest);
  }

  public UserRegistryNicknameAlreadyExistException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace,
      ResponseType responseType,
      ErrorType errorType,
      Collection<ConstraintViolation<PersonRegistrationDtoRequest>> violations,
      PersonRegistrationDtoRequest personRegistrationDtoRequest) {
    super(message, cause, enableSuppression, writableStackTrace, responseType, errorType,
        violations, personRegistrationDtoRequest);
  }
}
