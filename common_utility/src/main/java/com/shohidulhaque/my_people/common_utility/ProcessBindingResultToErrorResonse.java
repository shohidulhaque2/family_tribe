package com.shohidulhaque.my_people.common_utility;

import com.shohidulhaque.my_people.common_model.data_transfer_object.DisplayName;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorResponse;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.FieldError;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.FieldErrorResponse;
import com.shohidulhaque.my_people.common_model.validation.SensitiveData;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ProcessBindingResultToErrorResonse {

    public static ErrorResponse SyntaxError(BindingResult bindingResult, ErrorCode.ErrorType errorType, Class<?> responseClass) {
        ErrorResponse.ErrorResponseBuilder builder = ErrorResponse.builder();
        List<FieldError> errors = new LinkedList<>();
        if (bindingResult.hasErrors()) {
            //TODO: FIX THIS. TEST WITH GLOBAL. SHOULD NOT BE A FEILD ERROR
            for (ObjectError objectError : bindingResult.getGlobalErrors()) {
                errors.add(
                    new FieldError(
                        objectError.getObjectName(),
                        objectError.getObjectName(),
                        objectError.getDefaultMessage(),
                        objectError.getObjectName())
                );
            }
            Object obj = bindingResult.getTarget();
            List<Field> fieldsList = GetAllFields(obj.getClass());
            for (org.springframework.validation.FieldError fieldError : bindingResult.getFieldErrors()) {

                //try {

                    List<Field> matchedFieldList =  fieldsList.stream().filter(field -> field.getName().equals(fieldError.getField())).collect(
                        Collectors.toList());

                    if(matchedFieldList.isEmpty()){
                        continue;
                    }

                    //Field field = obj.getClass().getDeclaredField(fieldError.getField());
                    Field field = matchedFieldList.get(0);
                    //Field field = obj.getClass().getDeclaredField(fieldError.getField());

                    if (!Objects.isNull(field.getAnnotation(SensitiveData.class))) {
                        DisplayName displayName = field.getAnnotation(DisplayName.class);
                        errors.add(
                            new FieldError(
                                fieldError.getField(),
                                "", fieldError.getDefaultMessage(),
                                displayName.value())
                        );
                    } else {
                        //TODO: might not have one, check first. remove this.
                        //DisplayName displayName = field.getAnnotation(DisplayName.class);
                        errors.add(
                            new FieldError(
                                fieldError.getField(),
                                (String) fieldError.getRejectedValue(),
                                fieldError.getDefaultMessage(),
                                fieldError.getField())
                        );
                    }
                //} catch (NoSuchFieldException e) {
                    //TODO: log
                //}
            }
        }
        return builder
            .objectName(responseClass.getSimpleName())
            .list(errors)
            .type(errorType)
            .build();
    }

    private static List<Field> GetAllFields(Class clazz) {
        if (clazz == null) {
            return new LinkedList<>();
        }

        List<Field> result = GetAllFields(clazz.getSuperclass());
        Field [] fields = clazz.getDeclaredFields();
        for(Field f: fields){
            result.add(f);
        }
        return result;
    }

    public static <T> ErrorResponse SemanticError(
        BindingResult bindingResult,
        Collection<ConstraintViolation<T>> violations,
        ErrorCode.ErrorType errorType,
        Class<?> semanticExceptionClass) {
        //TODO: fix. take care of global error
        //TODO:org.springframework.validation.ObjectError

        for (ConstraintViolation v : violations) {
            org.springframework.validation.FieldError f = new org.springframework.validation.FieldError(
                bindingResult.getObjectName(),
                v.getPropertyPath().toString(), v.getMessage());
            bindingResult.addError(f);
        }
        ErrorResponse.ErrorResponseBuilder builder = ErrorResponse.builder();
        List<FieldError> errors = new LinkedList<>();
        if (bindingResult.hasErrors()) {
            //TODO: FIX THIS. TEST WITH GLOBAL. SHOULD NOT BE A FEILD ERROR
            for (ObjectError objectError : bindingResult.getGlobalErrors()) {
                errors.add(
                    new FieldError(
                        objectError.getObjectName(),
                        objectError.getObjectName(),
                        objectError.getDefaultMessage(),
                        objectError.getObjectName()));
            }

            Object obj = bindingResult.getTarget();
            List<Field> fieldsList = GetAllFields(obj.getClass());

            for (org.springframework.validation.FieldError fieldError : bindingResult.getFieldErrors()) {
                //try {

                    List<Field> matchedFieldList = fieldsList.stream().filter(field -> field.getName().equals(fieldError.getField())).collect(
                        Collectors.toList());


                    if(matchedFieldList.isEmpty()){
                        continue;
                    }

                    //Field field = obj.getClass().getDeclaredField(fieldError.getField());
                    Field field = matchedFieldList.get(0);

                    //Field field = obj.getClass().getDeclaredField(fieldError.getField());
                //TODO: remove DisplayName.class
                    if (!Objects.isNull(field.getAnnotation(SensitiveData.class))) {
                        DisplayName displayName = field.getAnnotation(DisplayName.class);
                        errors.add(
                            new FieldError(
                                fieldError.getField(),
                                "",
                                fieldError.getDefaultMessage(),
                                displayName.value())
                        );
                    } else {
                        //DisplayName displayName = field.getAnnotation(DisplayName.class);
                        errors.add(
                            new FieldError(
                                fieldError.getField(),
                                (String) fieldError.getRejectedValue(),
                                fieldError.getDefaultMessage(),
                                fieldError.getField()));
                    }
//                } catch (NoSuchFieldException e) {
//                    //should never happen
//                    //TODO: log
//                }
            }
        }
        return builder
            .objectName(semanticExceptionClass.getSimpleName())
            .list(errors)
            .type(errorType)
            .build();
    }

    public static List<List<FieldErrorResponse>> ErrorResponse(
        BindingResult bindingResult,
        Collection<? extends ConstraintViolation<?>> violations) {
        //TODO: fix. take care of global error
        //TODO:org.springframework.validation.ObjectError

        for(ConstraintViolation v : violations) {
            org.springframework.validation.FieldError f = new org.springframework.validation.FieldError(
                bindingResult.getObjectName(),
                v.getPropertyPath().toString(), v.getMessage());
            bindingResult.addError(f);
        }
        List<FieldErrorResponse> globalListErrors = new LinkedList<>();
        //ErrorResponse2.ErrorResponse2Builder builderGlobalError = ErrorResponse2.builder();
        //List<FieldError> globalErrors = new LinkedList<>();
        List<FieldErrorResponse> objectFieldErrors = new LinkedList<>();

        if (bindingResult.hasErrors()) {
            Object obj = bindingResult.getTarget();
            //builderGlobalError.objectName(obj.toString());
            //builderFieldError.objectName(obj.toString());
            //======================================================================================
            //TODO: FIX THIS. TEST WITH GLOBAL. SHOULD NOT BE A FIELD ERROR
            for (ObjectError objectError : bindingResult.getGlobalErrors()) {
                FieldErrorResponse.FieldErrorResponseBuilder errorResponse2Builder = FieldErrorResponse.builder();
                errorResponse2Builder.objectName(obj.toString());
                errorResponse2Builder.fieldError(
                    new FieldError(
                        objectError.getObjectName(),
                        objectError.getObjectName(),
                        objectError.getDefaultMessage(),
                        objectError.getObjectName()));
                globalListErrors.add(errorResponse2Builder.build());
            }
            //======================================================================================
            List<Field> fieldsList = GetAllFields(obj.getClass());
            for (org.springframework.validation.FieldError fieldError : bindingResult.getFieldErrors()) {
                List<Field> matchedFieldList = fieldsList.stream().filter(field -> field.getName().equals(fieldError.getField())).collect(
                    Collectors.toList());

                if(matchedFieldList.isEmpty()){
                    continue;
                }
                Field field = matchedFieldList.get(0);
                //TODO: remove DisplayName.class
                if (!Objects.isNull(field.getAnnotation(SensitiveData.class))) {
                    DisplayName displayName = field.getAnnotation(DisplayName.class);
                    FieldErrorResponse.FieldErrorResponseBuilder objectFieldErrorBuilder = FieldErrorResponse.builder();
                    objectFieldErrorBuilder.objectName(obj.toString());
                    objectFieldErrorBuilder.fieldError(new FieldError(
                                                            fieldError.getField(),
                                                            "",
                                                            fieldError.getDefaultMessage(),
                                                            displayName.value()));
                } else {
                    FieldErrorResponse.FieldErrorResponseBuilder objectFieldErrorBuilder = FieldErrorResponse.builder();
                    objectFieldErrorBuilder.objectName(obj.toString());
                    objectFieldErrorBuilder.fieldError(new FieldError(
                                                                fieldError.getField(),
                                                                (String) fieldError.getRejectedValue(),
                                                                fieldError.getDefaultMessage(),
                                                                fieldError.getField()));
                    objectFieldErrors.add(objectFieldErrorBuilder.build());
                }
            }
        }
        return List.of(globalListErrors, objectFieldErrors);
    }

}
