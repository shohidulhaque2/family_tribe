package com.shohidulhaque.user_registry_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OpenidConnectAccessTokenClaimsToSpringSecurityMapper {

    OpenidConnectAccessTokenClaimsToSpringSecurityMapper INSTANCE = Mappers
            .getMapper(OpenidConnectAccessTokenClaimsToSpringSecurityMapper.class);

}
