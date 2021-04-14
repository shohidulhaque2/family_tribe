package com.shohidulhaque.user_registry_service.message_keys;

public interface ValidationMessageKeys {

  //CREATE OBJECT AND CHECK ERROR MESSAGE EXIST IN PPOPERTIES FILE
  String ValidEmailValidationErrorMessageKey = "{com.shohidulhaque.user.registration_and_password.dto_validation.ValidEmail.message}";
  String PasswordMatchesValidationErrorMessageKey = "{com.shohidulhaque.user.registration_and_password.dto_validation.PasswordMatches.message}";
  String WeakPasswordValidationErrorMessageKey = "{com.shohidulhaque.user.registration_and_password.dto_validation.WeakPassword.message}";

  String UserEmailAlreadyExistValidationErrorMessageKey = "{com.shohidulhaque.user.registration_and_password.dto_validation.EmailShouldNotExist.message}";
  String UsernameAlreadyExistValidationErrorMessageKey = "{com.shohidulhaque.user.registration_and_password.dto_validation.UsernameShouldNotExist.message}";
  String UsernameNeedsToHaveValidNameKey = "{com.shohidulhaque.user.registration_and_password.dto_validation.UsernameNeedsToHaveValidNames.message}";
  String CannotBeBlankErrorMessageKey = "{javax.validation.constraints.NotBlank.message}";

}
