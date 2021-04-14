package com.shohidulhaque.user_registry_service.dto_validation;

import com.shohidulhaque.user_registry_service.message_keys.ValidationMessageKeys;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailShouldNotExistValidator.class)
@Documented
public @interface EmailShouldNotExist {

  String message() default ValidationMessageKeys.UserEmailAlreadyExistValidationErrorMessageKey;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
