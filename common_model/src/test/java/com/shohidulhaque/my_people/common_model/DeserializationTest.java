package com.shohidulhaque.my_people.common_model;

import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.FieldError;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.FieldErrorResponse;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DeserializationTest {

//    @Test
//    public void deserializationTest() throws Exception{
//        View v = new View();
//
//        v.setShapes(new ArrayList<>(List.of(Rectangle.of(3, 6), Circle.of(5))));
//
//        System.out.println("-- serializing --");
//        ObjectMapper om = new ObjectMapper();
//        String s = om.writeValueAsString(v);
//        System.out.println(s);
//
//        System.out.println("-- deserializing --");
//        View view = om.readValue(s, View.class);
//        System.out.println(view);
//
//    }

    @Test
    public void deserializationTest2() throws Exception {

        FieldError fieldError = new FieldError();
        fieldError.setErrorMessage("error");
        fieldError.setFieldName("field name");
        fieldError.setRejectedValue("rejected value");
        fieldError.setFieldDisplayName("display name");
        FieldErrorResponse fieldErrorResponse1 =  new FieldErrorResponse();
        fieldErrorResponse1.setObjectName("name 1");
        fieldErrorResponse1.setType(ErrorType.registration);
        fieldErrorResponse1.setFieldError(fieldError);

        FieldError fieldError2 = new FieldError();
        fieldError2.setErrorMessage("error2");
        fieldError2.setFieldName("field name2");
        fieldError2.setRejectedValue("rejected value2");
        fieldError2.setFieldDisplayName("display name2");
        FieldErrorResponse fieldErrorResponse2 =  new FieldErrorResponse();
        fieldErrorResponse2.setObjectName("name 2");
        fieldErrorResponse2.setType(ErrorType.registration);

        FieldError fieldError3 = new FieldError();
        fieldError3.setErrorMessage("error3");
        fieldError3.setFieldName("field name3");
        fieldError3.setRejectedValue("rejected value3");
        fieldError3.setFieldDisplayName("display name3");
        FieldErrorResponse fieldErrorResponse3 =  new FieldErrorResponse();
        fieldErrorResponse3.setObjectName("name 3");
        fieldErrorResponse3.setType(ErrorType.registration);

        List<FieldErrorResponse> fieldErrorResponseList = new LinkedList<>();
        fieldErrorResponseList.add(fieldErrorResponse1);
        fieldErrorResponseList.add(fieldErrorResponse2);
        fieldErrorResponseList.add(fieldErrorResponse3);

//        ContentGetRegisteredUserErrorResponse contentGetRegisteredUserErrorResponse = new ContentGetRegisteredUserErrorResponse();
//        contentGetRegisteredUserErrorResponse.setType(ErrorType.userProfileCreateUserProfileGeneral);
//        FieldErrorResponse [] fieldErrorResponseArray = new FieldErrorResponse [fieldErrorResponseList.size()];
//        contentGetRegisteredUserErrorResponse.setPayload(fieldErrorResponseList.toArray(fieldErrorResponseArray));
//        List<ContentGetRegisteredUserErrorResponse> contentGetRegisteredUserErrorResponseList = new LinkedList<>();
//        contentGetRegisteredUserErrorResponseList.add(contentGetRegisteredUserErrorResponse);
//        GetRegisteredUserDtoResponse v = GetRegisteredUserDtoResponse
//            .ErrorUserProfileDtoResponse(ResponseType.builder().code(0).subCode(0).message("").build(),
//                contentGetRegisteredUserErrorResponseList);
//        ObjectMapper om = new ObjectMapper();
//        String s = om.writerWithDefaultPrettyPrinter().writeValueAsString(v);
//        System.out.printf("%s\n", s);
//        GetRegisteredUserDtoResponse w = om.readValue(s, GetRegisteredUserDtoResponse.class);
//        System.out.printf("%s\n", s);
    }
}
