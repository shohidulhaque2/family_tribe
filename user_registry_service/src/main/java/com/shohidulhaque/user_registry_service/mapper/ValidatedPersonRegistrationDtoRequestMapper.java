package com.shohidulhaque.user_registry_service.mapper;

import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedPersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.model.PersonRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ValidatedPersonRegistrationDtoRequestMapper {

    ValidatedPersonRegistrationDtoRequestMapper INSTANCE = Mappers
        .getMapper(ValidatedPersonRegistrationDtoRequestMapper.class);

    PersonRegistration to(
        ValidatedPersonRegistrationDtoRequest validatedPersonRegistrationDtoRequest);

}
