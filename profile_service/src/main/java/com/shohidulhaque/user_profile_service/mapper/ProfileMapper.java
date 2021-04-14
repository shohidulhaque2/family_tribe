package com.shohidulhaque.user_profile_service.mapper;

import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UpdateProfileDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfile;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoRequest;
import com.shohidulhaque.user_profile_service.data_model.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers
        .getMapper(ProfileMapper.class);

    UserProfile to(
        Profile profile);

    Profile to(UserProfile userProfile);
    Profile to(UserProfileDtoRequest userProfileDtoRequest);
    Profile to(UpdateProfileDtoRequest userProfileDtoRequest);

    Profile merge(@MappingTarget Profile existingProfile, UserProfile newUserProfile);

    Profile merge(@MappingTarget Profile existingProfile, UserProfileDtoRequest newUserProfile);

    //TODO: ignore nulls patch merge, fix issue with Date Of Birth Not Being Copied Properly
    @Mapping(source = "id" , target = "id", ignore = true)
    @Mapping(source = "personUuid", target = "personUuid", ignore = true)
    Profile merge(@MappingTarget Profile existingProfile, Profile updatedProfile);

}
