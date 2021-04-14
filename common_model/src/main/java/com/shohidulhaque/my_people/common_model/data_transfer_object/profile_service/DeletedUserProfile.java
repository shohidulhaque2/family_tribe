package com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@SuperBuilder
public class DeletedUserProfile {
    UUID uuid;
}
