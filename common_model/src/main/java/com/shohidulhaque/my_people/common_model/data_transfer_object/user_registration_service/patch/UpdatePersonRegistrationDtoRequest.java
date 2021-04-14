package com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.patch;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.PersonRegistrationDtoRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@SuperBuilder
public class UpdatePersonRegistrationDtoRequest extends PersonRegistrationDtoRequest {

    static Logger Logger = LoggerFactory.getLogger(UpdatePersonRegistrationDtoRequest.class);

    protected  String firstName;

    protected  String lastName;

}
