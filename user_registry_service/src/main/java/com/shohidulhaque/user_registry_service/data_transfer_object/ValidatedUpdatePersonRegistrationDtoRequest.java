package com.shohidulhaque.user_registry_service.data_transfer_object;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.patch.UpdatePersonRegistrationDtoRequest;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@SuperBuilder
@AllArgsConstructor
@ToString
public class ValidatedUpdatePersonRegistrationDtoRequest extends
    UpdatePersonRegistrationDtoRequest {

    @NotBlank
    @Length(max = 255)
    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @NotBlank
    @Length(max = 255)
    @Override
    public String getLastName() {
        return super.getLastName();
    }
}
