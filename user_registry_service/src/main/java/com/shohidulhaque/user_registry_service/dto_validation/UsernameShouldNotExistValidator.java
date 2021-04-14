package com.shohidulhaque.user_registry_service.dto_validation;

import com.shohidulhaque.user_registry_service.message_keys.ValidationMessageKeys;
import com.shohidulhaque.user_registry_service.repository.PersonRegistrationRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UsernameShouldNotExistValidator implements
    ConstraintValidator<UsernameShouldNotExist, String> {

  @Autowired
  public UsernameShouldNotExistValidator(
      PersonRegistrationRepository personRegistrationRepository) {
    this.userRegistrationRepository = personRegistrationRepository;
  }

  PersonRegistrationRepository userRegistrationRepository;

  //ignore this method.
  @Override
  public void initialize(UsernameShouldNotExist constraintAnnotation) {
  }

  @Override
  public boolean isValid(String username, ConstraintValidatorContext context) {
    if (this.userRegistrationRepository.nicknameCount(username) != 0) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(
              ValidationMessageKeys.UsernameAlreadyExistValidationErrorMessageKey)
          .addConstraintViolation();
      return false;
    }

    //add errors here
    return true;
  }

}
