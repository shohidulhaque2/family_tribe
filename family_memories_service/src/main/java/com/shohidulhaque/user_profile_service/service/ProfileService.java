package com.shohidulhaque.user_profile_service.service;

import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.ContentUserProfileSuccessResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.CreateProfileDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.DeletedUserProfile;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UpdateProfileDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfile;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoResponse;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.Undefined;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.success_codes.SuccessCode.SuccessType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import com.shohidulhaque.user_profile_service.data_model.Profile;
import com.shohidulhaque.user_profile_service.data_transfer_object.validation.ProfileDtoArgumentViolationResponse;
import com.shohidulhaque.user_profile_service.mapper.ProfileMapper;
import com.shohidulhaque.user_profile_service.repository.ProfileRepository;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileService {
    //==============================================================================================
    ProfileRepository profileRepository;
    ProfileMapper profileMapper;
    //Validator validator;
    javax.validation.Validator javaxValidator;
    //==============================================================================================
    @Autowired
    public ProfileService(
        ProfileRepository profileRepository,
        ProfileMapper profileMapper,
        javax.validation.Validator javaxValidator){
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.javaxValidator = javaxValidator;
    }
    //==============================================================================================
    public UserProfileDtoResponse createUserProfile(CreateProfileDtoRequest createProfileDtoRequest) {
        Set<ConstraintViolation<UserProfileDtoRequest>> argumentViolations =  this.javaxValidator.validate(createProfileDtoRequest);
        if (!argumentViolations.isEmpty()) {
            return ProfileDtoArgumentViolationResponse.BadArgumentException(
                createProfileDtoRequest,
                argumentViolations,
                ErrorType.registrationCreateUserUserNameAlreadyExist
            );
        }
        Optional<Profile> existingProfileOptional = this.profileRepository.findProfile(createProfileDtoRequest.getUuidOfPerson());
        if(existingProfileOptional.isPresent()){
            ResponseType possibleErrorResponseType = new ResponseType(HttpStatus.CONFLICT.toString(),
                HttpStatus.CONFLICT.value(),
                Undefined.undefined.subCode);
            return ProfileDtoArgumentViolationResponse.GetResponseBodyForError(possibleErrorResponseType, ErrorType.userProfileCreateUserProfileGeneral);
        }
        Profile profile = this.profileMapper.to(createProfileDtoRequest);
        profile.setUuid(UUID.randomUUID());
        profile.setPersonUuid(createProfileDtoRequest.getUuidOfPerson());
        Instant now = Instant.now();
        profile.setCreationTime(now);
        profile.setUpdateTime(now);
        profile =  this.profileRepository.save(profile);
        return profileHasBeenCreatedSuccessfully(profile);
    }
    private UserProfileDtoResponse profileHasBeenCreatedSuccessfully(Profile profile) {
        UserProfile userProfile = this.profileMapper.to(profile);
        ContentUserProfileSuccessResponse contentUserProfileSuccessResponse =
            ContentUserProfileSuccessResponse.builder().type(SuccessType.general).payload(userProfile).build();
        ResponseType responseType = ResponseType
            .builder()
            .message(HttpStatus.CREATED.toString())
            .code(HttpStatus.CREATED.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
        UserProfileDtoResponse userProfileDtoResponse = UserProfileDtoResponse.SuccessUserProfileDtoResponse(responseType, List.of(contentUserProfileSuccessResponse));
        return userProfileDtoResponse;
    }
    //==============================================================================================
    public UserProfileDtoResponse updateUserProfile(UUID userProfileId, UpdateProfileDtoRequest updateProfileDtoRequest) {
        Optional<Profile> existingProfileOptional = this.profileRepository.findProfile(userProfileId);
        if(existingProfileOptional.isEmpty()){
            ResponseType possibleErrorResponseType = new ResponseType(HttpStatus.NOT_FOUND.toString(),
                HttpStatus.NOT_FOUND.value(),
                Undefined.undefined.subCode);
            return ProfileDtoArgumentViolationResponse.GetResponseBodyForError(possibleErrorResponseType, ErrorType.userProfileUpdateUserProfileUserDoesNotExist);
        }
        Profile existingProfile = existingProfileOptional.get();
        CopyFieldsToIfNull(existingProfile, updateProfileDtoRequest);
        Set<ConstraintViolation<UserProfileDtoRequest>> argumentViolations =  this.javaxValidator.validate(updateProfileDtoRequest);
        if (!argumentViolations.isEmpty()) {
            return ProfileDtoArgumentViolationResponse.BadArgumentException(
                updateProfileDtoRequest,
                argumentViolations,
                ErrorType.userProfileUpdateUserProfileBadArgumentsToCreateUserProfile
            );
        }
        Profile updatedProfile = this.profileMapper.to(updateProfileDtoRequest);
        this.profileMapper.merge(existingProfile, updatedProfile);
        existingProfile.setUpdateTime(Instant.now());
        return profileHasBeenUpdatedSuccessfully(existingProfile);
    }
    private UserProfileDtoResponse profileHasBeenUpdatedSuccessfully(Profile profile) {
        UserProfile userProfile = this.profileMapper.to(profile);
        ContentUserProfileSuccessResponse contentUserProfileSuccessResponse =
            ContentUserProfileSuccessResponse.builder().type(SuccessType.general).payload(userProfile).build();
        ResponseType responseType = ResponseType
            .builder()
            .message(HttpStatus.OK.toString())
            .code(HttpStatus.OK.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
        UserProfileDtoResponse userProfileDtoResponse = UserProfileDtoResponse.SuccessUserProfileDtoResponse(responseType, List.of(contentUserProfileSuccessResponse));
        return userProfileDtoResponse;
    }
    private static void CopyFieldsToIfNull(Profile profile, UpdateProfileDtoRequest updateProfileDtoRequest){
        if(Objects.isNull(updateProfileDtoRequest.getFirstName())){
            updateProfileDtoRequest.setFirstName(profile.getFirstName());
        }
        if(Objects.isNull(updateProfileDtoRequest.getLastName())){
            updateProfileDtoRequest.setLastName(profile.getLastName());
        }
        if(Objects.isNull(updateProfileDtoRequest.getBirthDay())){
            updateProfileDtoRequest.setBirthDay(profile.getBirthDate());
        }
    }
    //==============================================================================================
    public UserProfileDtoResponse deleteUserProfile(UUID userProfileId) {
        Optional<Profile> existingProfileOptional = this.profileRepository.findProfile(userProfileId);
        if(existingProfileOptional.isEmpty()){
            ResponseType possibleErrorResponseType = new ResponseType(HttpStatus.NOT_FOUND.toString(),
                HttpStatus.NOT_FOUND.value(),
                Undefined.undefined.subCode);
            return ProfileDtoArgumentViolationResponse.GetResponseBodyForError(possibleErrorResponseType, ErrorType.userProfileUpdateUserProfileUserDoesNotExist);
        }
        UUID uuid = existingProfileOptional.get().getUuid();
        this.profileRepository.delete(existingProfileOptional.get());
        return profileHasBeenDeletedSuccessfully(uuid);
    }
    private UserProfileDtoResponse profileHasBeenDeletedSuccessfully(UUID uuid) {
        DeletedUserProfile deletedUserProfile = new DeletedUserProfile(uuid);
        ContentUserProfileSuccessResponse contentUserProfileSuccessResponse =
            ContentUserProfileSuccessResponse.builder().type(SuccessType.general).payload(deletedUserProfile).build();
        ResponseType responseType = ResponseType
            .builder()
            .message(HttpStatus.OK.toString())
            .code(HttpStatus.OK.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
        UserProfileDtoResponse userProfileDtoResponse = UserProfileDtoResponse.SuccessUserProfileDtoResponse(responseType, List.of(contentUserProfileSuccessResponse));
        return userProfileDtoResponse;
    }
    //==============================================================================================
    public UserProfileDtoResponse getUserProfile(UUID userProfileId) {
        Optional<Profile> existingProfileOptional = this.profileRepository.findProfile(userProfileId);
        if(existingProfileOptional.isEmpty()){
            ResponseType possibleErrorResponseType = new ResponseType(HttpStatus.NOT_FOUND.toString(),
                HttpStatus.NOT_FOUND.value(),
                Undefined.undefined.subCode);
            return ProfileDtoArgumentViolationResponse.GetResponseBodyForError(possibleErrorResponseType, ErrorType.userProfileUpdateUserProfileUserDoesNotExist);
        }
        Profile existingProfile = existingProfileOptional.get();
        return profileHasBeenRetrievedSuccessfully(existingProfile);
    }
    private UserProfileDtoResponse profileHasBeenRetrievedSuccessfully(Profile profile) {
        UserProfile userProfile = this.profileMapper.to(profile);
        ContentUserProfileSuccessResponse contentUserProfileSuccessResponse =
            ContentUserProfileSuccessResponse.builder().type(SuccessType.general).payload(userProfile).build();
        ResponseType responseType = ResponseType
            .builder()
            .message(HttpStatus.OK.toString())
            .code(HttpStatus.OK.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
        UserProfileDtoResponse userProfileDtoResponse = UserProfileDtoResponse.SuccessUserProfileDtoResponse(responseType, List.of(contentUserProfileSuccessResponse));
        return userProfileDtoResponse;
    }
    //==============================================================================================
}
