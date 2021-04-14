package com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.slf4j.LoggerFactory;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@SuperBuilder
public class CreatePersonRegistrationDtoRequest extends PersonRegistrationDtoRequest{

    static org.slf4j.Logger Logger = LoggerFactory.getLogger(CreatePersonRegistrationDtoRequest.class);

    public static String EMPTY_PASSWORD_VALUE = "";

    @Builder.Default
    protected  String firstName = "";

    @Builder.Default
    protected  String lastName = "";

    @Builder.Default
    protected  String nickname = "";

    @Builder.Default
    protected String password = "";

    @Builder.Default
    protected String matchingPassword = "";

    @Builder.Default
    protected String email = "";

}
