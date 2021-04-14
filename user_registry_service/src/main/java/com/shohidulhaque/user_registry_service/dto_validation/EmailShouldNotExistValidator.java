package com.shohidulhaque.user_registry_service.dto_validation;

import com.shohidulhaque.user_registry_service.message_keys.ValidationMessageKeys;
import com.shohidulhaque.user_registry_service.repository.PersonRegistrationRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

//TODO: change to UniqueEmailValidator
public class EmailShouldNotExistValidator implements
    ConstraintValidator<EmailShouldNotExist, String> {

  @Autowired
  public EmailShouldNotExistValidator(
      PersonRegistrationRepository personRegistrationRepository) {
    this.personRegistrationRepository = personRegistrationRepository;
  }

  PersonRegistrationRepository personRegistrationRepository;

  //ignore this method.
  @Override
  public void initialize(EmailShouldNotExist constraintAnnotation) {
  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    if (this.personRegistrationRepository.emailCount(email) != 0) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(
              ValidationMessageKeys.UserEmailAlreadyExistValidationErrorMessageKey)
          .addConstraintViolation();
      return false;
    }
    return true;
  }

}
