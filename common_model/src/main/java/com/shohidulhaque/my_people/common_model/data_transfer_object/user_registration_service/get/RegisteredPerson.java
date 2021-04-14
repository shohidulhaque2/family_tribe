package com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.get;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Data
@SuperBuilder
public class RegisteredPerson implements Serializable {
    protected UUID uuid;

    protected String firstName;

    protected String lastName;

    protected String nickname;

    protected String email;
}
