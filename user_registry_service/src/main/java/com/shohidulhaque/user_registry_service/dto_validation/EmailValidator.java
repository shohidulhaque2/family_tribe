package com.shohidulhaque.user_registry_service.dto_validation;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

@Deprecated
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private static final String EMAIL_PATTERN = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

  final static Pattern EmailRegex = Pattern.compile(EMAIL_PATTERN);

  @Override
  public void initialize(ValidEmail constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return StringUtils.hasText(value) && EmailRegex.matcher(value).matches();
  }


}
