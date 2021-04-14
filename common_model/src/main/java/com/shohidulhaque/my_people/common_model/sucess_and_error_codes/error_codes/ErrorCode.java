package com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.Undefined;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ERROR.GENERAL;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ERROR.REGISTRATION.CREATE.PASSWORD_AND_RE_TYPED_PASSWORD_DO_NOT_MATCH;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ErrorCode {

    static final Logger Logger = LoggerFactory.getLogger(ErrorCode.class);

    static class ErrorTypeInformation{
        public ErrorTypeInformation(int code, int subCode, String message){
            this.code = code;
            this.subCode = subCode;
            this.message = message;
        }

        public int getCode() {
            return code;
        }
        public int getSubCode() {
            return subCode;
        }
        public String getMessage() {
            return this.message;
        }
        String message;
        int code;
        int subCode;
    }

    @ToString
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum ErrorType {
        //==========================================================================================
        //UNDEFINED
        undefined(Undefined.undefined.getCode(),
            Undefined.undefined.getSubCode(),
            Undefined.undefined.getMessage()),
        //==========================================================================================
        //GENERAL ERROR
        general(ERROR.GENERAL.CODE,
                GENERAL.SUB_CODE,
                ERROR.GENERAL.SUB_MESSAGE),
        //==========================================================================================
        //==========================================================================================
        //TODO: should be done like PROFILE
        //REGISTATION
        registration(ERROR.REGISTRATION.CODE,
            ERROR.REGISTRATION.GENERAL.CODE,
            ERROR.REGISTRATION.GENERAL.MESSAGE),

        generalRegistrationCreate(ERROR.REGISTRATION.CODE,
            ERROR.REGISTRATION.CREATE.GENERAL.CODE,
            ERROR.REGISTRATION.CREATE.GENERAL.MESSAGE),

        registrationCreateUserUserNameAlreadyExist(ERROR.REGISTRATION.CODE,
            ERROR.REGISTRATION.CREATE.USERNAME_ALREADY_EXIST.CODE,
            ERROR.REGISTRATION.CREATE.USERNAME_ALREADY_EXIST.MESSAGE),

        userEmailAddressAlreadyExistError(ERROR.REGISTRATION.CODE,
            ERROR.REGISTRATION.CREATE.EMAIL_ADDRESS_ALREADY_EXIST.CODE,
            ERROR.REGISTRATION.CREATE.EMAIL_ADDRESS_ALREADY_EXIST.MESSAGE),

        userPassordwordQualityIssueError(ERROR.REGISTRATION.CODE,
            ERROR.REGISTRATION.CREATE.USER_PASSWORD_QUALITY_ISSUE.CODE,
            ERROR.REGISTRATION.CREATE.USER_PASSWORD_QUALITY_ISSUE.MESSAGE),

        passwordAndReTypedPasswordDoNotMatch(ERROR.REGISTRATION.CODE,
            PASSWORD_AND_RE_TYPED_PASSWORD_DO_NOT_MATCH.CODE,
            PASSWORD_AND_RE_TYPED_PASSWORD_DO_NOT_MATCH.MESSAGE),
        //==========================================================================================
        //==========================================================================================
        //TODO: refactor. should be done like the profile
        //CHAT SPACE
        generalChatSpaceError(ERROR.USER_CHAT_SPACE.CODE,
            ERROR.USER_CHAT_SPACE.GENERAL.CODE,
            ERROR.USER_CHAT_SPACE.GENERAL.MESSAGE),

        generalGetChatSpace(ERROR.USER_CHAT_SPACE.CODE,
            ERROR.USER_CHAT_SPACE.GET.GENERAL.CODE,
            ERROR.USER_CHAT_SPACE.GET.GENERAL.MESSAGE),

        createChatSpaceGeneralError(ERROR.USER_CHAT_SPACE.CODE,
            ERROR.USER_CHAT_SPACE.CREATE.GENERAL.CODE,
            ERROR.USER_CHAT_SPACE.CREATE.GENERAL.MESSAGE),

        createChatSpaceDoesNotExist(ERROR.USER_CHAT_SPACE.CODE,
            ERROR.USER_CHAT_SPACE.CREATE.USER_DOES_NOT_EXIST.CODE,
            ERROR.USER_CHAT_SPACE.CREATE.USER_DOES_NOT_EXIST.MESSAGE),

        createChatSpaceNameAlreadyExist(ERROR.USER_CHAT_SPACE.CODE,
            ERROR.USER_CHAT_SPACE.CREATE.CHAT_SPACE_NAME_ALREADY_EXIST.CODE,
            ERROR.USER_CHAT_SPACE.CREATE.CHAT_SPACE_NAME_ALREADY_EXIST.MESSAGE
        ),

        createChatSpaceBadArguments(ERROR.USER_CHAT_SPACE.CODE,
            ERROR.USER_CHAT_SPACE.CREATE.BAD_ARGUMENTS.CODE,
            ERROR.USER_CHAT_SPACE.CREATE.BAD_ARGUMENTS.MESSAGE),
        //==========================================================================================
        //==========================================================================================
        //USER PROFILE
        userProfile(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.GENERAL.CODE,
            ERROR.USER_PROFILE.GENERAL.MESSAGE),

        //CREATE PROFILE CREATE
        userProfileCreateUserProfileGeneral(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.CREATE_USER_PROFILE_GENERAL.CODE,
            ERROR.USER_PROFILE.CREATE_USER_PROFILE_GENERAL.MESSAGE),
        userProfileCreateUserProfileBadUserIdArgument(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.CREATE_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT.CODE,
            ERROR.USER_PROFILE.CREATE_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT.MESSAGE),
        userProfileCreateUserProfileUserDoesNotExist(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.CREATE_USER_PROFILE_USER_DOES_NOT_EXIST.CODE,
            ERROR.USER_PROFILE.CREATE_USER_PROFILE_USER_DOES_NOT_EXIST.MESSAGE),
        userProfileCreateUserProfileBadArgumentsToCreateUserProfile(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.CREATE_USER_PROFILE_BAD_ARGUMENTS_TO_CREATE_PROFILE.CODE,
            ERROR.USER_PROFILE.CREATE_USER_PROFILE_BAD_ARGUMENTS_TO_CREATE_PROFILE.MESSAGE),
        userProfileCreateUserProfileProfileAlreadyExist(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.CREATE_USER_PROFILE_USER_PROFILE_ALREADY_EXIST.CODE,
            ERROR.USER_PROFILE.CREATE_USER_PROFILE_USER_PROFILE_ALREADY_EXIST.MESSAGE),

        //GET USER PROFILE
        userProfileGetProfileGeneral(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.GET_USER_PROFILE_GENERAL.CODE,
            ERROR.USER_PROFILE.GET_USER_PROFILE_GENERAL.MESSAGE),
        userProfileGetUserProfileBadUserIdArgument(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.GET_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT.CODE,
            ERROR.USER_PROFILE.GET_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT .MESSAGE),
        userProfileGetUserProfileBadProfileIdArgument(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.GET_USER_PROFILE_USER_PROFILE_ID_IS_A_BAD_ARGUMENT.CODE,
            ERROR.USER_PROFILE.GET_USER_PROFILE_USER_PROFILE_ID_IS_A_BAD_ARGUMENT.MESSAGE),
        userProfileGetUserProfileUserDoesNotExist(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.GET_USER_PROFILE_USER_DOES_NOT_EXIST.CODE,
            ERROR.USER_PROFILE.GET_USER_PROFILE_USER_DOES_NOT_EXIST.MESSAGE),
        userProfileGetUserProfileUserProfileDoesNotExist(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.GET_USER_PROFILE_USER_PROFILE_DOES_NOT_EXIST.CODE,
            ERROR.USER_PROFILE.GET_USER_PROFILE_USER_PROFILE_DOES_NOT_EXIST.MESSAGE),

        //DELETE USER PROFILE
        userProfileDeleteUserProfileGeneral(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.DELETE_USER_PROFILE_GENERAL.CODE,
            ERROR.USER_PROFILE.DELETE_USER_PROFILE_GENERAL.MESSAGE),
        userProfileDeleteUserProfileBadUserIdArgument(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.DELETE_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT.CODE,
            ERROR.USER_PROFILE.DELETE_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT.MESSAGE),
        userProfileDeleteUserProfileBadUserProfileIdArgument(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.DELETE_USER_PROFILE_USER_PROFILE_ID_IS_A_BAD_ARGUMENT.CODE,
            ERROR.USER_PROFILE.DELETE_USER_PROFILE_USER_PROFILE_ID_IS_A_BAD_ARGUMENT.MESSAGE),
        userProfileDeleteUserProfileUserDoesNotExist(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.DELETE_USER_PROFILE_USER_DOES_NOT_EXIST.CODE,
            ERROR.USER_PROFILE.DELETE_USER_PROFILE_USER_DOES_NOT_EXIST.MESSAGE),
        userProfileDeleteUserProfileUserProfileDoesNotExist(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.DELETE_USER_PROFILE_USER_PROFILE_DOES_NOT_EXIST.CODE,
            ERROR.USER_PROFILE.DELETE_USER_PROFILE_USER_PROFILE_DOES_NOT_EXIST.MESSAGE),

        //USER PROFILE UPDATE
        userProfileUpdateUserProfileGeneral(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.UPDATE_USER_PROFILE_GENERAL.CODE,
            ERROR.USER_PROFILE.UPDATE_USER_PROFILE_GENERAL.MESSAGE),
        userProfileUpdateUserProfileBadUserIdArgument(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.UPDATE_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT.CODE,
            ERROR.USER_PROFILE.UPDATE_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT.MESSAGE),
        userProfileUpdateUserProfileBadUserProfileIdArgument(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.UPDATE_USER_PROFILE_USER_PROFILE_ID_IS_A_BAD_ARGUMENT.CODE,
            ERROR.USER_PROFILE.UPDATE_USER_PROFILE_USER_PROFILE_ID_IS_A_BAD_ARGUMENT.MESSAGE),
        userProfileUpdateUserProfileUserDoesNotExist(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.UPDATE_USER_PROFILE_USER_DOES_NOT_EXIST.CODE,
            ERROR.USER_PROFILE.UPDATE_USER_PROFILE_USER_DOES_NOT_EXIST.MESSAGE),
        userProfileUpdateUserProfileUserProfileDoesNotExist(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.UPDATE_USER_PROFILE_USER_PROFILE_DOES_NOT_EXIST.CODE,
            ERROR.USER_PROFILE.UPDATE_USER_PROFILE_USER_PROFILE_DOES_NOT_EXIST.MESSAGE),
        userProfileUpdateUserProfileBadArgumentsToCreateUserProfile(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.UPDATE_USER_PROFILE_BAD_ARGUMENTS_TO_UPDATE_USER_PROFILE.CODE,
            ERROR.USER_PROFILE.UPDATE_USER_PROFILE_BAD_ARGUMENTS_TO_UPDATE_USER_PROFILE.MESSAGE),

        //TODO:CREATE USER - NOT NEEDED
        userProfileCreateUserGeneral(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.CREATE_USER_GENERAL.CODE,
            ERROR.USER_PROFILE.CREATE_USER_GENERAL.MESSAGE),
        userProfileCreateUserBadUserIdArgument(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.CREATE_USER_USER_ALREADY_EXIST.CODE,
            ERROR.USER_PROFILE.CREATE_USER_USER_ALREADY_EXIST.MESSAGE),
        userProfileCreateUserUserAlreadyExist(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.CREATE_USER_USER_USER_ID_IS_A_BAD_ARGUMENT.CODE,
            ERROR.USER_PROFILE.CREATE_USER_USER_USER_ID_IS_A_BAD_ARGUMENT.MESSAGE),

        //TODO: DELETE USER - NOT NEEDED
        userProfileDeleteUserGeneral(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.DELETE_USER_GENERAL.CODE,
            ERROR.USER_PROFILE.DELETE_USER_GENERAL.MESSAGE),
        userProfileDeleteUserBadUserIdArgument(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.DELETE_USER_USER_ID_IS_A_BAD_ARGUMENT.CODE,
            ERROR.USER_PROFILE.DELETE_USER_USER_ID_IS_A_BAD_ARGUMENT.MESSAGE),
        userProfileDeleteUserUserDoesNotExist(ERROR.USER_PROFILE.CODE,
            ERROR.USER_PROFILE.DELETE_USER_USER_DOES_NOT_EXIST.CODE,
            ERROR.USER_PROFILE.DELETE_USER_USER_DOES_NOT_EXIST.MESSAGE),
        //==========================================================================================
        //CREATE MAILBOX
        userMailboxGeneral(ERROR.USER_MAILBOX.CODE,
            ERROR.USER_MAILBOX.GENERAL.CODE,
            ERROR.USER_MAILBOX.GENERAL.MESSAGE),

        userMailboxCreateUserMailboxGeneral(ERROR.USER_MAILBOX.CODE,
            ERROR.USER_MAILBOX.CREATE.CODE,
            ERROR.USER_MAILBOX.CREATE.MESSAGE),

        userMailboxUpdateUserMailboxGeneral(ERROR.USER_MAILBOX.CODE,
            ERROR.USER_MAILBOX.UPDATE.CODE,
            ERROR.USER_MAILBOX.UPDATE.MESSAGE),

        userMailboxDeleteUserMailboxGeneral(ERROR.USER_MAILBOX.CODE,
            ERROR.USER_MAILBOX.DELETE.CODE,
            ERROR.USER_MAILBOX.DELETE.MESSAGE),

        userMailboxGetUserMailboxGeneral(ERROR.USER_MAILBOX.CODE,
            ERROR.USER_MAILBOX.GET.CODE,
            ERROR.USER_MAILBOX.GET.MESSAGE),

        userMailCreateMailGeneral(ERROR.USER_MAILBOX.CODE,
            ERROR.USER_MAILBOX.CREATE_USER_MAIL_GENERAL.CODE,
            ERROR.USER_MAILBOX.CREATE_USER_MAIL_GENERAL.MESSAGE),

        userMailUpdateUserMailGeneral(ERROR.USER_MAILBOX.CODE,
            ERROR.USER_MAILBOX.UPDATE_USER_MAIL_GENERAL.CODE,
            ERROR.USER_MAILBOX.UPDATE_USER_MAIL_GENERAL.MESSAGE);
        //==========================================================================================
        //==========================================================================================

        @JsonCreator
        static ErrorType FindValue(@JsonProperty("code") int code,
            @JsonProperty("subCode") int subCode) {

            switch (code) {

                case 0: {
                        return undefined;
                }

                case ERROR.GENERAL.CODE: {

                    switch (subCode) {

                        case ERROR.GENERAL.SUB_CODE: {
                            return general;
                        }
                        default: {
                            Logger.warn("unable to find " + ErrorType.class.getName() + " during deserialization of [code = " + code + ", sub_code = " + subCode + "] for enum ");
                            Logger.warn(undefined.getClass().getName() + " returned when matching values to " + ErrorType.class.getName() + ". this is only returned because nothing else matched.");
                            return undefined;
                        }

                    }

                }

                case ERROR.REGISTRATION.CODE: {

                    switch (subCode) {

                        case ERROR.REGISTRATION.GENERAL.CODE: {
                            return registration;
                        }

                        case ERROR.REGISTRATION.CREATE.GENERAL.CODE: {
                            return generalRegistrationCreate;
                        }

                        case ERROR.REGISTRATION.CREATE.USERNAME_ALREADY_EXIST.CODE: {
                            return registrationCreateUserUserNameAlreadyExist;
                        }

                        case ERROR.REGISTRATION.CREATE.EMAIL_ADDRESS_ALREADY_EXIST.CODE: {
                            return userEmailAddressAlreadyExistError;
                        }

                        case ERROR.REGISTRATION.CREATE.USER_PASSWORD_QUALITY_ISSUE.CODE:{
                            return userPassordwordQualityIssueError;
                        }

                        case ERROR.REGISTRATION.CREATE.PASSWORD_AND_RE_TYPED_PASSWORD_DO_NOT_MATCH.CODE: {
                            return passwordAndReTypedPasswordDoNotMatch;
                        }

                        default: {
                            Logger.warn("unable to find " + ErrorType.class.getName() + " during deserialization of [code = " + code + ", sub_code = " + subCode + "] for enum ");
                            Logger.warn(undefined.getClass().getName() + " returned when matching values to " + ErrorType.class.getName() + ". this is only returned because nothing else matched.");
                            return undefined;
                        }

                    }

                }

                case ERROR.USER_CHAT_SPACE.CODE: {
                    switch (subCode) {

                        case ERROR.USER_CHAT_SPACE.GENERAL.CODE: {
                            return generalChatSpaceError;
                        }

                        case ERROR.USER_CHAT_SPACE.GET.GENERAL.CODE: {
                            return generalGetChatSpace;
                        }

                        case ERROR.USER_CHAT_SPACE.CREATE.GENERAL.CODE: {
                            return createChatSpaceGeneralError;
                        }

                        case ERROR.USER_CHAT_SPACE.CREATE.CHAT_SPACE_NAME_ALREADY_EXIST.CODE:{
                            return createChatSpaceNameAlreadyExist;
                        }

                        case ERROR.USER_CHAT_SPACE.CREATE.USER_DOES_NOT_EXIST.CODE: {
                            return createChatSpaceDoesNotExist;
                        }

                        case ERROR.USER_CHAT_SPACE.CREATE.BAD_ARGUMENTS.CODE: {
                            return createChatSpaceBadArguments;
                        }

                        default: {
                            Logger.warn("unable to find " + ErrorType.class.getName() + " during deserialization of [code = " + code + ", sub_code = " + subCode + "] for enum ");
                            Logger.warn(undefined.getClass().getName() + " returned when matching values to " + ErrorType.class.getName() + ". this is only returned because nothing else matched.");
                            return undefined;
                        }
                    }
                }

                case ERROR.USER_PROFILE.CODE: {
                    switch (subCode) {
                        //==========================================================================
                        case ERROR.USER_PROFILE.GENERAL.CODE: {
                            return userProfile;
                        }
                        //==========================================================================
                        case ERROR.USER_PROFILE.CREATE_USER_PROFILE_GENERAL.CODE: {
                            return userProfileCreateUserProfileGeneral;
                        }
                        case ERROR.USER_PROFILE.CREATE_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT.CODE : {
                            return userProfileCreateUserProfileBadUserIdArgument;
                        }
                        case ERROR.USER_PROFILE.CREATE_USER_PROFILE_USER_DOES_NOT_EXIST.CODE : {
                            return userProfileCreateUserProfileUserDoesNotExist;
                        }
                        case ERROR.USER_PROFILE.CREATE_USER_PROFILE_BAD_ARGUMENTS_TO_CREATE_PROFILE.CODE : {
                            return userProfileCreateUserProfileBadArgumentsToCreateUserProfile;
                        }
                        case ERROR.USER_PROFILE.CREATE_USER_PROFILE_USER_PROFILE_ALREADY_EXIST.CODE : {
                            return userProfileCreateUserProfileProfileAlreadyExist;
                        }
                        //==========================================================================
                        case ERROR.USER_PROFILE.GET_USER_PROFILE_GENERAL.CODE: {
                            return userProfileGetProfileGeneral;
                        }
                        case ERROR.USER_PROFILE.GET_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT.CODE : {
                            return userProfileGetUserProfileBadUserIdArgument;
                        }
                        case ERROR.USER_PROFILE.GET_USER_PROFILE_USER_DOES_NOT_EXIST.CODE : {
                            return userProfileGetUserProfileUserDoesNotExist;
                        }
                        case ERROR.USER_PROFILE.GET_USER_PROFILE_USER_PROFILE_DOES_NOT_EXIST.CODE : {
                            return userProfileGetUserProfileUserProfileDoesNotExist;
                        }
                        case ERROR.USER_PROFILE.GET_USER_PROFILE_USER_PROFILE_ID_IS_A_BAD_ARGUMENT.CODE : {
                            return userProfileGetUserProfileBadProfileIdArgument;
                        }
                        //==========================================================================
                        case ERROR.USER_PROFILE.DELETE_USER_PROFILE_GENERAL.CODE: {
                            return userProfileDeleteUserProfileGeneral;
                        }
                        case ERROR.USER_PROFILE.DELETE_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT.CODE : {
                            return userProfileDeleteUserProfileBadUserIdArgument;
                        }
                        case ERROR.USER_PROFILE.DELETE_USER_PROFILE_USER_PROFILE_DOES_NOT_EXIST.CODE : {
                            return userProfileDeleteUserProfileUserProfileDoesNotExist;
                        }
                        case ERROR.USER_PROFILE.DELETE_USER_PROFILE_USER_DOES_NOT_EXIST.CODE : {
                            return userProfileDeleteUserProfileUserDoesNotExist;
                        }
                        case ERROR.USER_PROFILE.DELETE_USER_PROFILE_USER_PROFILE_ID_IS_A_BAD_ARGUMENT.CODE: {
                            return userProfileDeleteUserProfileBadUserProfileIdArgument;
                        }
                        //==========================================================================
                        case ERROR.USER_PROFILE.UPDATE_USER_PROFILE_GENERAL.CODE: {
                            return userProfileUpdateUserProfileGeneral;
                        }
                        case ERROR.USER_PROFILE.UPDATE_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT.CODE : {
                            return userProfileUpdateUserProfileBadUserIdArgument;
                        }
                        case ERROR.USER_PROFILE.UPDATE_USER_PROFILE_USER_PROFILE_ID_IS_A_BAD_ARGUMENT.CODE : {
                            return userProfileUpdateUserProfileBadUserProfileIdArgument;
                        }
                        case ERROR.USER_PROFILE.UPDATE_USER_PROFILE_USER_DOES_NOT_EXIST.CODE : {
                            return userProfileUpdateUserProfileUserDoesNotExist;
                        }
                        case ERROR.USER_PROFILE.UPDATE_USER_PROFILE_USER_PROFILE_DOES_NOT_EXIST.CODE : {
                            return userProfileUpdateUserProfileUserProfileDoesNotExist;
                        }
                        case ERROR.USER_PROFILE.UPDATE_USER_PROFILE_BAD_ARGUMENTS_TO_UPDATE_USER_PROFILE.CODE: {
                            return userProfileUpdateUserProfileBadArgumentsToCreateUserProfile;
                        }
                        //==========================================================================
                        case ERROR.USER_PROFILE.CREATE_USER_GENERAL.CODE: {
                            return userProfileCreateUserGeneral;
                        }
                        case ERROR.USER_PROFILE.CREATE_USER_USER_ALREADY_EXIST.CODE: {
                            return userProfileCreateUserUserAlreadyExist;
                        }
                        case ERROR.USER_PROFILE.CREATE_USER_USER_USER_ID_IS_A_BAD_ARGUMENT.CODE: {
                            return userProfileCreateUserBadUserIdArgument;
                        }
                        //==========================================================================
                        case ERROR.USER_PROFILE.DELETE_USER_GENERAL.CODE: {
                            return userProfileDeleteUserGeneral;
                        }
                        case ERROR.USER_PROFILE.DELETE_USER_USER_DOES_NOT_EXIST.CODE: {
                            return userProfileDeleteUserUserDoesNotExist;
                        }
                        case ERROR.USER_PROFILE.DELETE_USER_USER_ID_IS_A_BAD_ARGUMENT.CODE: {
                            return userProfileDeleteUserBadUserIdArgument;
                        }
                        //==========================================================================
                        default: {
                            Logger.warn("unable to find " + ErrorType.class.getName() + " during deserialization of [code = " + code + ", sub_code = " + subCode + "] for enum ");
                            Logger.warn(undefined.getClass().getName() + " returned when matching values to " + ErrorType.class.getName() + ". this is only returned because nothing else matched.");
                            return undefined;
                        }
                    }
                }

                case ERROR.USER_MAILBOX.CODE: {
                    return undefined;
                }

                default: {
                    Logger.warn("unable to find " + ErrorType.class.getName() + " during deserialization of [code = " + code + ", sub_code = " + subCode + "] for enum ");
                    Logger.warn(undefined.getClass().getName() + " returned when matching values to " + ErrorType.class.getName() + ". this is only returned because nothing else matched.");
                    return undefined;
                }
            }
        }

        ErrorType(int code, int subCode, String message) {
            this.code = code;
            this.subCode = subCode;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public int getSubCode() {
            return subCode;
        }

        public String getMessage() {
            return this.message;
        }

        String message;
        int code;
        int subCode;
    }

    static class ERROR{

        public static class GENERAL {
            public static final int CODE = -1;
            public static final int SUB_CODE = -1;
            public static final String SUB_MESSAGE = "";
        }

        static class REST_API_SERVER {

        }

        static class REGISTRATION{
            public static final int CODE = -2;
            public static class GENERAL {
                public static final int CODE = -1;
                public static final String MESSAGE = "Error when performing registration operation";
            }
            public static class CREATE {
                public static class GENERAL {
                    public static final int CODE = -2;
                    public static final String MESSAGE = "Unable to create user.";
                }
                public static class USERNAME_ALREADY_EXIST {
                    public static final int CODE = -3;
                    public static final String MESSAGE = "Nickname already exist.";
                    public static final ErrorTypeInformation errorType = new ErrorTypeInformation(REGISTRATION.CODE, USERNAME_ALREADY_EXIST.CODE, USERNAME_ALREADY_EXIST.MESSAGE);
                }
                public static class EMAIL_ADDRESS_ALREADY_EXIST {
                    public static final int CODE = -4;
                    public static final String MESSAGE = "Email already exist.";
                }
                public static class USER_PASSWORD_QUALITY_ISSUE {
                    public static final int CODE = -5;
                    public static final String MESSAGE = "Password is not good enough.";
                }
                public static class PASSWORD_AND_RE_TYPED_PASSWORD_DO_NOT_MATCH {
                    public static final int CODE = -6;
                    public static final String MESSAGE = "Password and re-typed password do not match.";
                }
            }

        }

        public static class  USER_CHAT_SPACE {
            static final int CODE = -3;

            public static class GENERAL {
                static final int CODE = -1;
                static final String MESSAGE = "User chat space error.";
            }
            public static class GET {
                public static class GENERAL {
                    static final int CODE = -2;
                    static final String MESSAGE = "Unable to get user's chat space.";
                }
            }
            public static class CREATE {

                public static class GENERAL {
                    static final int CODE = -3;
                    static final String MESSAGE = "Unable to create new chat space.";
                }

                public static class USER_DOES_NOT_EXIST {
                    static final int CODE = -4;
                    static final String MESSAGE = "The user does not exist.";
                }

                public static class CHAT_SPACE_NAME_ALREADY_EXIST {

                    static final int CODE = -5;
                    static final String MESSAGE = "The name already exist.";
                }

                public static class BAD_ARGUMENTS {
                    static final int CODE = -6;
                    static final String MESSAGE = "Bad user input provided.";
                }
            }
        }

        public static class  USER_PROFILE {
            static final int CODE = -4;
            //======================================================================================
            public static class GENERAL {
                static final int CODE = -1;
                static final String MESSAGE = "User profile error.";
            }
            //======================================================================================
            public static class GET_USER_PROFILE_GENERAL{
                static final int CODE = -2;
                static final String MESSAGE = "Unable to get user's profile.";
            }

            public static class GET_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT{
                static final int CODE = -3;
                static final String MESSAGE = "User ID is invalid.";
            }

            public static class GET_USER_PROFILE_USER_DOES_NOT_EXIST{
                static final int CODE = -4;
                static final String MESSAGE = "User does not exist.";
            }

            public static class GET_USER_PROFILE_USER_PROFILE_DOES_NOT_EXIST {
                static final int CODE = -5;
                static final String MESSAGE = "User profile does not exist.";
            }

            //======================================================================================
            public static class CREATE_USER_PROFILE_GENERAL{
                static final int CODE = -6;
                static final String MESSAGE = "Unable to create a new user profile.";
            }

            public static class CREATE_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT{
                static final int CODE = -7;
                static final String MESSAGE = "User ID is invalid.";
            }

            public static class CREATE_USER_PROFILE_USER_DOES_NOT_EXIST {
                static final int CODE = -8;
                static final String MESSAGE = "User does not exist.";
            }

            public static class CREATE_USER_PROFILE_BAD_ARGUMENTS_TO_CREATE_PROFILE {
                static final int CODE = -9;
                static final String MESSAGE = "Bad arguments provided to create user profile.";
            }

            public static class CREATE_USER_PROFILE_USER_PROFILE_ALREADY_EXIST {
                static final int CODE = -10;
                static final String MESSAGE = "User profile already exist.";
            }
            //======================================================================================
            public static class DELETE_USER_PROFILE_GENERAL {
                static final int CODE = -11;
                static final String MESSAGE = "Unable to delete a user's profile.";
            }

            public static class DELETE_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT {
                static final int CODE = -12;
                static final String MESSAGE = "User ID is invalid.";
            }

            public static class DELETE_USER_PROFILE_USER_DOES_NOT_EXIST {
                static final int CODE = -13;
                static final String MESSAGE = "User does not exist.";
            }

            public static class DELETE_USER_PROFILE_USER_PROFILE_DOES_NOT_EXIST {
                static final int CODE = -14;
                static final String MESSAGE = "User profile does not exist.";
            }
            //======================================================================================
            public static class UPDATE_USER_PROFILE_GENERAL {
                static final int CODE = -15;
                static final String MESSAGE = "Unable to update a user's profile.";
            }

            public static class UPDATE_USER_PROFILE_USER_ID_IS_A_BAD_ARGUMENT {
                static final int CODE = -16;
                static final String MESSAGE = "User ID is invalid.";
            }

            public static class UPDATE_USER_PROFILE_USER_DOES_NOT_EXIST {
                static final int CODE = -17;
                static final String MESSAGE = "User does not exist.";
            }

            public static class UPDATE_USER_PROFILE_USER_PROFILE_DOES_NOT_EXIST {
                static final int CODE = -18;
                static final String MESSAGE = "User profile does not exist.";
            }

            public static class UPDATE_USER_PROFILE_BAD_ARGUMENTS_TO_UPDATE_USER_PROFILE {
                static final int CODE = -19;
                static final String MESSAGE = "Bad arguments provided to create user profile.";
            }

            //======================================================================================
            public static class CREATE_USER_GENERAL {
                static final int CODE = -20;
                static final String MESSAGE = "Create user error.";
            }

            public static class CREATE_USER_USER_ALREADY_EXIST {
                static final int CODE = -21;
                static final String MESSAGE = "User already exist.";
            }

            public static class CREATE_USER_USER_USER_ID_IS_A_BAD_ARGUMENT {
                static final int CODE = -22;
                static final String MESSAGE = "User ID is invalid.";
            }
            //======================================================================================
            public static class DELETE_USER_GENERAL {
                static final int CODE = -23;
                static final String MESSAGE = "Delete user error.";
            }

            public static class DELETE_USER_USER_DOES_NOT_EXIST {
                static final int CODE = -24;
                static final String MESSAGE = "User does not exist.";
            }

            public static class DELETE_USER_USER_ID_IS_A_BAD_ARGUMENT{
                static final int CODE = -25;
                static final String MESSAGE = "User ID is invalid.";
            }
            //======================================================================================
            public static class  GET_USER_PROFILE_USER_PROFILE_ID_IS_A_BAD_ARGUMENT {
                static final int CODE = -26;
                static final String MESSAGE = "User profile ID is invalid.";
            }
            public static class  DELETE_USER_PROFILE_USER_PROFILE_ID_IS_A_BAD_ARGUMENT {
                static final int CODE = -27;
                static final String MESSAGE = "User profile ID is invalid.";
            }
            public static class UPDATE_USER_PROFILE_USER_PROFILE_ID_IS_A_BAD_ARGUMENT {
                static final int CODE = -28;
                static final String MESSAGE = "User profile ID is invalid.";
            }
            //======================================================================================
        }

        public static class  USER_MAILBOX {
            static final int CODE = -5;
            public static class GENERAL {
                static final int CODE = -1;
                static final String MESSAGE = "User mailbox error.";
            }
            //======================================================================================
            public static class GET{
                static final int CODE = -2;
                static final String MESSAGE = "General get mailbox error.";
            }
            public static class DELETE{
                static final int CODE = -3;
                static final String MESSAGE = "General delete mailbox error.";
            }
            public static class UPDATE{
                static final int CODE = -4;
                static final String MESSAGE = "General update mailbox error";
            }
            public static class CREATE{
                static final int CODE = -5;
                static final String MESSAGE = "General create mailbox error.";
            }
            //======================================================================================
            public static class GET_USER_MAILBOX_GENERAL{
                static final int CODE = -6;
                static final String MESSAGE = "Unable to get user's mailbox.";
            }

            public static class GET_USER_MAILBOX_USER_ID_IS_A_BAD_ARGUMENT{
                static final int CODE = -7;
                static final String MESSAGE = "User ID is invalid.";
            }

            public static class GET_USER_MAILBOX_USER_DOES_NOT_EXIST{
                static final int CODE = -8;
                static final String MESSAGE = "User does not exist.";
            }

            public static class GET_USER_MAILBOX_USER_MAILBOX_DOES_NOT_EXIST {
                static final int CODE = -9;
                static final String MESSAGE = "User's mailbox does not exist.";
            }
            //======================================================================================
            public static class CREATE_USER_MAILBOX_GENERAL{
                static final int CODE = -10;
                static final String MESSAGE = "Unable to create a new user mailbox.";
            }

            public static class CREATE_USER_MAILBOX_USER_ID_IS_A_BAD_ARGUMENT{
                static final int CODE = -11;
                static final String MESSAGE = "User ID is invalid.";
            }

            public static class CREATE_USER_MAILBOX_USER_DOES_NOT_EXIST {
                static final int CODE = -12;
                static final String MESSAGE = "User does not exist.";
            }

            public static class CREATE_USER_MAILBOX_BAD_ARGUMENTS_TO_CREATE_MAILBOX {
                static final int CODE = -13;
                static final String MESSAGE = "Bad arguments provided to create user mailbox.";
            }

            public static class CREATE_USER_MAILBOX_USER_MAILBOX_ALREADY_EXIST {
                static final int CODE = -14;
                static final String MESSAGE = "User mailbox already exist.";
            }
            //======================================================================================
            public static class DELETE_USER_MAILBOX {
                static final int CODE = -15;
                static final String MESSAGE = "Delete user error.";
            }

            public static class DELETE_USER_MAILBOX_USER_DOES_NOT_EXIST {
                static final int CODE = -16;
                static final String MESSAGE = "User does not exist.";
            }

            public static class DELETE_USER_MAILBOX_USER_ID_IS_A_BAD_ARGUMENT{
                static final int CODE = -17;
                static final String MESSAGE = "User ID is invalid.";
            }
            //======================================================================================
            public static class UPDATE_USER_MAILBOX_GENERAL {
                static final int CODE = -18;
                static final String MESSAGE = "Unable to update a user's mailbox.";
            }

            public static class UPDATE_USER_MAILBOX_USER_ID_IS_A_BAD_ARGUMENT {
                static final int CODE = -19;
                static final String MESSAGE = "User ID is invalid.";
            }

            public static class UPDATE_USER_MAILBOX_USER_DOES_NOT_EXIST {
                static final int CODE = -20;
                static final String MESSAGE = "User does not exist.";
            }

            public static class UPDATE_USER_MAILBOX_USER_MAILBOX_DOES_NOT_EXIST {
                static final int CODE = -21;
                static final String MESSAGE = "User mailbox does not exist.";
            }

            public static class UPDATE_USER_MAILBOX_BAD_ARGUMENTS_TO_UPDATE_USER_MAILBOX {
                static final int CODE = -22;
                static final String MESSAGE = "Bad arguments provided to update user mailbox.";
            }
            //======================================================================================
            public static class CREATE_USER_MAIL_GENERAL{
                static final int CODE = -23;
                static final String MESSAGE = "Unable to create a new user mailbox.";
            }

            public static class CREATE_USER_MAIL_USER_ID_IS_A_BAD_ARGUMENT{
                static final int CODE = -24;
                static final String MESSAGE = "User ID is invalid.";
            }

            public static class CREATE_USER_MAIL_USER_DOES_NOT_EXIST {
                static final int CODE = -25;
                static final String MESSAGE = "User does not exist.";
            }

            public static class CREATE_USER_MAIL_BAD_ARGUMENTS_TO_CREATE_MAIL {
                static final int CODE = -26;
                static final String MESSAGE = "Bad arguments provided to create user mailbox.";
            }

            public static class CREATE_USER_MAIL_USER_MAIL_ALREADY_EXIST {
                static final int CODE = -27;
                static final String MESSAGE = "User mailbox already exist.";
            }
            //======================================================================================
            public static class UPDATE_USER_MAIL_GENERAL {
                static final int CODE = -28;
                static final String MESSAGE = "Unable to update a user's mail.";
            }

            public static class UPDATE_USER_MAIL_USER_ID_IS_A_BAD_ARGUMENT {
                static final int CODE = -29;
                static final String MESSAGE = "mail ID is invalid.";
            }

            public static class UPDATE_USER_MAIL_USER_DOES_NOT_EXIST {
                static final int CODE = -30;
                static final String MESSAGE = "Mail does not exist.";
            }

            public static class UPDATE_USER_MAIL_USER_MAIL_DOES_NOT_EXIST {
                static final int CODE = -31;
                static final String MESSAGE = "User mail does not exist.";
            }

            public static class UPDATE_USER_MAIL_BAD_ARGUMENTS_TO_UPDATE_USER_MAIL {
                static final int CODE = -32;
                static final String MESSAGE = "Bad arguments provided to update user mail.";
            }
            //======================================================================================
        }
    }

}
