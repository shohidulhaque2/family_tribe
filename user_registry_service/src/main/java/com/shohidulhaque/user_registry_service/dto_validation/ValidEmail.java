package com.shohidulhaque.user_registry_service.dto_validation;

import com.shohidulhaque.user_registry_service.message_keys.ValidationMessageKeys;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmailValidator.class)
public @interface ValidEmail {
  String message() default ValidationMessageKeys.ValidEmailValidationErrorMessageKey;
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
