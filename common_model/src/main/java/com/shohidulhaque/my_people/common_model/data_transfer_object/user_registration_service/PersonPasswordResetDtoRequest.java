package com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@SuperBuilder
public class PersonPasswordResetDtoRequest extends PersonRegistrationDtoRequest{

    UUID uuid;
    String email;
    String nickname;

}
