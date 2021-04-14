package com.shohidulhaque.my_people.common_model.exception.user_registry_service;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.PersonRegistrationDtoRequest;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import java.util.Collection;
import java.util.LinkedList;
import javax.validation.ConstraintViolation;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class UserRegistryServiceValidationException extends UserRegistryServiceException {

    Collection<ConstraintViolation<PersonRegistrationDtoRequest>> violations = new LinkedList<>();
    PersonRegistrationDtoRequest personRegistrationDtoRequest;

    public UserRegistryServiceValidationException(
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<PersonRegistrationDtoRequest>> violations,
        PersonRegistrationDtoRequest personRegistrationDtoRequest) {
        super(responseType, errorType);
        this.violations = violations;
        this.personRegistrationDtoRequest = personRegistrationDtoRequest;
    }

    public UserRegistryServiceValidationException(String message,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<PersonRegistrationDtoRequest>> violations,
        PersonRegistrationDtoRequest personRegistrationDtoRequest) {
        super(message, responseType, errorType);
        this.violations = violations;
        this.personRegistrationDtoRequest = personRegistrationDtoRequest;
    }

    public UserRegistryServiceValidationException(String message, Throwable cause,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<PersonRegistrationDtoRequest>> violations,
        PersonRegistrationDtoRequest personRegistrationDtoRequest) {
        super(message, cause, responseType, errorType);
        this.violations = violations;
        this.personRegistrationDtoRequest = personRegistrationDtoRequest;
    }

    public UserRegistryServiceValidationException(Throwable cause,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<PersonRegistrationDtoRequest>> violations,
        PersonRegistrationDtoRequest personRegistrationDtoRequest) {
        super(cause, responseType, errorType);
        this.violations = violations;
        this.personRegistrationDtoRequest = personRegistrationDtoRequest;
    }

    public UserRegistryServiceValidationException(String message, Throwable cause,
        boolean enableSuppression, boolean writableStackTrace,
        ResponseType responseType,
        ErrorType errorType,
        Collection<ConstraintViolation<PersonRegistrationDtoRequest>> violations,
        PersonRegistrationDtoRequest personRegistrationDtoRequest) {
        super(message, cause, enableSuppression, writableStackTrace, responseType, errorType);
        this.violations = violations;
        this.personRegistrationDtoRequest = personRegistrationDtoRequest;
    }
}
