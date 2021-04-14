package com.shohidulhaque.user_registry_service.dto_validation;

import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedPersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.message_keys.ValidationMessageKeys;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class PasswordMatchesValidator implements
    ConstraintValidator<PasswordMatches, ValidatedPersonRegistrationDtoRequest> {

  //TODO: might be able to extract feild name here
  @Override
  public void initialize(PasswordMatches constraintAnnotation) {
  }

  @Override
  public boolean isValid(ValidatedPersonRegistrationDtoRequest validatedPersonRegistrationDtoRequest, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();
    if (!StringUtils.hasText(validatedPersonRegistrationDtoRequest.getPassword())) {
      context
          .buildConstraintViolationWithTemplate(ValidationMessageKeys.CannotBeBlankErrorMessageKey)
          .addPropertyNode("password")
          .addConstraintViolation();
      return false;
    } else if (!StringUtils.hasText(validatedPersonRegistrationDtoRequest.getMatchingPassword())) {
      context
          .buildConstraintViolationWithTemplate(ValidationMessageKeys.CannotBeBlankErrorMessageKey)
          .addPropertyNode("matchingPassword")
          .addConstraintViolation();
      return false;

    } else if (StringUtils.hasText(validatedPersonRegistrationDtoRequest.getPassword()) &&
        StringUtils.hasText(validatedPersonRegistrationDtoRequest.getMatchingPassword()) &&
        !validatedPersonRegistrationDtoRequest.getPassword().strip().equals(validatedPersonRegistrationDtoRequest.getMatchingPassword().strip())) {
      context
          .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode("matchingPassword")
          .addConstraintViolation();
      return false;
    } else {
      return true;
    }
  }

}
