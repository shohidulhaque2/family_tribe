package com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.get.RegisteredPerson;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ContentPersonRegistrationSuccessResponsePayload implements Serializable {
    protected RegisteredPerson userProfile;
}
