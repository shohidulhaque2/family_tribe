package com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class UserProfile implements Serializable {
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @NotBlank
    String birthDay;
}
