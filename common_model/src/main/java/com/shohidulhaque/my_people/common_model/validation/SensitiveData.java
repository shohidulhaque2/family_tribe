package com.shohidulhaque.my_people.common_model.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation marks sensitive data that needs to be carefully treated.
 * <p>
 * An example is a user's password should not be returned back to them if validation for the
 * password fails during a rest api call.
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveData {

}
