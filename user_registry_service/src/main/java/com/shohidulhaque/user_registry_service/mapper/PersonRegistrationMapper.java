package com.shohidulhaque.user_registry_service.mapper;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.get.RegisteredPerson;
import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedUpdatePersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.model.PersonRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonRegistrationMapper {

    PersonRegistrationMapper INSTANCE = Mappers
        .getMapper(PersonRegistrationMapper.class);

    RegisteredPerson to(PersonRegistration personRegistration);

//    @Mapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, target = "firstName")
//    @Mapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, target = "lastName")
//    void copyValueIfFeildIsNullInValidatedUpdatePersonRegistrationDtoRequest
//        (PersonRegistration personRegistration, @MappingTarget ValidatedUpdatePersonRegistrationDtoRequest validatedUpdatePersonRegistrationDtoRequest);

    @Mapping(target = "firstName")
    @Mapping(target = "lastName")
    void to(ValidatedUpdatePersonRegistrationDtoRequest validatedUpdatePersonRegistrationDtoRequest, @MappingTarget  PersonRegistration personRegistration);

}
