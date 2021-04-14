package com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateProfileDtoRequest extends UserProfileDtoRequest{

    @NotBlank(message = "{com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoRequest.firstName.NotBlank.message}")
    @Length(min = 1, max = 256, message = "{com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoRequest.firstName.Length.message}")
    String firstName;

    @NotBlank(message = "{com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoRequest.lastName.NotBlank.message}")
    @Length(min = 1, max = 256, message = "{com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoRequest.lastName.Length.message}")
    String lastName;

    @NotBlank(message = "{com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoRequest.birthDay.NotBlank.message}")
    @Length(min = 1, max = 256, message = "{com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoRequest.birthDay.Length.message}")
    String birthDay;

}
