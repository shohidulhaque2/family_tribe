package com.shohidulhaque.user_registry_service.dto_validation;

import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedPersonRegistrationDtoRequest;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.beans.factory.annotation.Autowired;

public class NotWeakPasswordValidator implements
    ConstraintValidator<NotWeakPassword, ValidatedPersonRegistrationDtoRequest> {

  PasswordValidator passwordValidator;

  @Autowired
  public NotWeakPasswordValidator(PasswordValidator passwordValidator) {
    this.passwordValidator = passwordValidator;
  }

  //ignore this.
  @Override
  public void initialize(NotWeakPassword constraintAnnotation) {
  }

  @Override
  public boolean isValid(ValidatedPersonRegistrationDtoRequest validatedPersonRegistrationDtoRequest, ConstraintValidatorContext context) {
    RuleResult result = passwordValidator.validate(new PasswordData(validatedPersonRegistrationDtoRequest.getPassword()));
    if (!result.isValid()) {
      List<String> weakPasswordErrorMessage = passwordValidator.getMessages(result);
      for (String errorMessage : weakPasswordErrorMessage) {
        context.disableDefaultConstraintViolation();
        context
            .buildConstraintViolationWithTemplate(errorMessage)
            .addPropertyNode("password")
            .addConstraintViolation();
      }
      return false;
    }
    return true;
  }

}
