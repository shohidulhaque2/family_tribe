package com.shohidulhaque.user_profile_service.endpoints;

import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.CreateProfileDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UpdateProfileDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoResponse;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.Undefined;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import com.shohidulhaque.user_profile_service.data_transfer_object.validation.ProfileDtoArgumentViolationResponse;
import com.shohidulhaque.user_profile_service.mapper.ProfileMapper;
import com.shohidulhaque.user_profile_service.service.ProfileService;
import java.util.UUID;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RequestMapping("/api/v1/user-profile-service")
@RestController
public class UserProfileEndPoints {

    static org.slf4j.Logger Logger = LoggerFactory.getLogger(UserProfileEndPoints.class);

    //==============================================================================================
    //general exception handlers
    @ExceptionHandler({Exception.class})
    public ResponseEntity<UserProfileDtoResponse> handleException(Exception e) {
        Logger.warn("exception handled by general error handling code.", e);
        ResponseType errorResponseType = new ResponseType(
            HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Undefined.undefined.subCode);
        UserProfileDtoResponse userProfileDtoResponse = ProfileDtoArgumentViolationResponse.GetResponseBodyForError(errorResponseType,ErrorType.userProfile);
        return ResponseEntity.status(userProfileDtoResponse.getContent().getResponseType().getCode()).body(userProfileDtoResponse);
    }
    //==============================================================================================
    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<UserProfileDtoResponse> handleAllDataAccessException(
        DataAccessException e) {
        Logger.warn("data access exception", e);
        ResponseType errorResponseType = new ResponseType(
            HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Undefined.undefined.subCode);
        UserProfileDtoResponse userProfileDtoResponse = ProfileDtoArgumentViolationResponse.GetResponseBodyForError(errorResponseType,ErrorType.userProfile);
        return ResponseEntity.status(userProfileDtoResponse.getContent().getResponseType().getCode()).body(userProfileDtoResponse);
    }
    //==============================================================================================
    ProfileService profileService;
    ProfileMapper profileMapper;
    Validator validator;
    javax.validation.Validator javaxValidator;
    //==============================================================================================
    @Autowired
    public UserProfileEndPoints(
        ProfileService profileService,
        Validator validator,
        javax.validation.Validator javaxValidator,
        ProfileMapper profileMapper) {
        this.profileService = profileService;
        this.validator = validator;
        this.javaxValidator = javaxValidator;
        this.profileMapper = profileMapper;
    }
    //==============================================================================================
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profiles/{userProfileId}")
    public ResponseEntity<UserProfileDtoResponse> getUserProfile(
        @PathVariable("userProfileId") UUID uuidOfPerson) {
        UserProfileDtoResponse userProfileDtoResponse = this.profileService.getUserProfile(uuidOfPerson);
        userProfileDtoResponse.add(WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder
                                            .methodOn(UserProfileEndPoints.class)
                                            .getUserProfile(uuidOfPerson))
                                        .withSelfRel());
        return ResponseEntity
                .status(userProfileDtoResponse.getContent().getResponseType().getCode())
                .body(userProfileDtoResponse);
    }
    //==============================================================================================
    //user profile crud operations
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/profiles/{userProfileId}")
    public ResponseEntity<UserProfileDtoResponse> updateUserProfile(
        @PathVariable("userProfileId") UUID uuidOfPerson,
        @RequestBody UpdateProfileDtoRequest updateProfileDtoRequest) {
        UserProfileDtoResponse userProfileDtoResponse = this.profileService.updateUserProfile(uuidOfPerson , updateProfileDtoRequest);
        userProfileDtoResponse.add(WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder
                                            .methodOn(UserProfileEndPoints.class)
                                            .updateUserProfile(uuidOfPerson,updateProfileDtoRequest))
                                        .withSelfRel());
        return ResponseEntity
            .status(userProfileDtoResponse.getContent().getResponseType().getCode())
            .body(userProfileDtoResponse);
    }
    //==============================================================================================
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/profiles/{userProfileId}")
    public ResponseEntity<UserProfileDtoResponse> deleteUserProfile(
        @PathVariable("userProfileId") UUID uuidOfPerson) {
        UserProfileDtoResponse userProfileDtoResponse = this.profileService.deleteUserProfile(uuidOfPerson);
        userProfileDtoResponse.add(WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(UserProfileEndPoints.class)
                .deleteUserProfile(uuidOfPerson))
            .withSelfRel());
        return ResponseEntity
            .status(userProfileDtoResponse.getContent().getResponseType().getCode())
            .body(userProfileDtoResponse);
    }
    //==============================================================================================
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profiles")
    public ResponseEntity<UserProfileDtoResponse> createUserProfile(@RequestBody CreateProfileDtoRequest createProfileDtoRequest) {
        UserProfileDtoResponse userProfileDtoResponse = this.profileService.createUserProfile(createProfileDtoRequest);
        userProfileDtoResponse.add(WebMvcLinkBuilder
                                    .linkTo(WebMvcLinkBuilder
                                        .methodOn(UserProfileEndPoints.class)
                                        .createUserProfile(createProfileDtoRequest))
                                    .withSelfRel());
        return ResponseEntity
                .status(userProfileDtoResponse.getContent().getResponseType().getCode())
                .body(userProfileDtoResponse);
    }
    //==============================================================================================
}
