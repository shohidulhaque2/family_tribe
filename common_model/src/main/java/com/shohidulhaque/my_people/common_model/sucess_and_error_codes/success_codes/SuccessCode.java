package com.shohidulhaque.my_people.common_model.sucess_and_error_codes.success_codes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.Undefined;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.success_codes.SuccessCode.SUCCESS.USER_MAILBOX;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.success_codes.SuccessCode.SUCCESS.USER_PROFILE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//https://stackoverflow.com/questions/29510759/how-to-test-spring-security-oauth2-resource-server-security
//https://docs.spring.io/spring-security/site/docs/5.0.x/reference/htmlsingle/#test-method-withsecuritycontext
public interface SuccessCode {

    Logger Logger = LoggerFactory.getLogger(SuccessCode.class);

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    enum SuccessType {
        undefined(Undefined.undefined.getCode(),
            Undefined.undefined.getSubCode(),
            Undefined.undefined.getMessage()),
        //==========================================================================================
        general(SUCCESS.GENERAL.CODE,
            SUCCESS.GENERAL.SUB_CODE,
            SUCCESS.GENERAL.MESSAGE),
        //==========================================================================================
        registration(SUCCESS.REGISTRATION.CODE,
            SUCCESS.REGISTRATION.GENERAL.CODE,
            SUCCESS.REGISTRATION.GENERAL.MESSAGE),
        //==========================================================================================
        userSecurityInformation(SUCCESS.USER_SECURITY_INFORMATION.CODE,
            SUCCESS.USER_SECURITY_INFORMATION.GENERAL.CODE,
            SUCCESS.USER_SECURITY_INFORMATION.GENERAL.MESSAGE),
        //==========================================================================================
        userChatSpace(SUCCESS.USER_CHAT_SPACE.CODE,
            SUCCESS.USER_CHAT_SPACE.GENERAL.CODE,
            SUCCESS.USER_CHAT_SPACE.GENERAL.MESSAGE),

        getChatSpace(SUCCESS.USER_CHAT_SPACE.CODE,
            SUCCESS.USER_CHAT_SPACE.GET.GENERAL.CODE,
            SUCCESS.USER_CHAT_SPACE.GET.GENERAL.MESSAGE),

        createChatSpace(SUCCESS.USER_CHAT_SPACE.CODE,
            SUCCESS.USER_CHAT_SPACE.CREATE.GENERAL.CODE,
            SUCCESS.USER_CHAT_SPACE.CREATE.GENERAL.MESSAGE),
        //==========================================================================================
        userProfileGeneral(SUCCESS.USER_PROFILE.CODE,
            SUCCESS.USER_PROFILE.GENERAL.CODE,
            SUCCESS.USER_PROFILE.GENERAL.MESSAGE),

        userProfileCreateUserGeneral(SUCCESS.USER_PROFILE.CODE,
            SUCCESS.USER_PROFILE.CREATE_USER.GENERAL.CODE,
            SUCCESS.USER_PROFILE.CREATE_USER.GENERAL.MESSAGE),

        userProfileDeleteUserGeneral(SUCCESS.USER_PROFILE.CODE,
            SUCCESS.USER_PROFILE.DELETE_USER.GENERAL.CODE,
            SUCCESS.USER_PROFILE.DELETE_USER.GENERAL.MESSAGE),

        userProfileCreateUserProfileGeneral(SUCCESS.USER_PROFILE.CODE,
            SUCCESS.USER_PROFILE.CREATE.GENERAL.CODE,
            SUCCESS.USER_PROFILE.CREATE.GENERAL.MESSAGE),

        userProfileGetUserProfileGeneral(SUCCESS.USER_PROFILE.CODE,
            SUCCESS.USER_PROFILE.GET.GENERAL.CODE,
            SUCCESS.USER_PROFILE.GET.GENERAL.MESSAGE),

        userProfileDeleteUserProfileGeneral(SUCCESS.USER_PROFILE.CODE,
            SUCCESS.USER_PROFILE.DELETE.GENERAL.CODE,
            SUCCESS.USER_PROFILE.DELETE.GENERAL.MESSAGE),

        userProfileUpdateUserProfileGeneral(SUCCESS.USER_PROFILE.CODE,
            SUCCESS.USER_PROFILE.UPDATE.GENERAL.CODE,
            SUCCESS.USER_PROFILE.UPDATE.GENERAL.MESSAGE),
        //==========================================================================================
        mailboxGeneral(
            SUCCESS.USER_MAILBOX.CODE,
            SUCCESS.USER_MAILBOX.GENERAL.CODE,
            SUCCESS.USER_MAILBOX.GENERAL.MESSAGE),
        mailboxCreateGeneral(
            SUCCESS.USER_MAILBOX.CODE,
            SUCCESS.USER_MAILBOX.CREATE_MAILBOX.GENERAL.CODE,
            SUCCESS.USER_MAILBOX.CREATE_MAILBOX.GENERAL.MESSAGE),
        mailboxGetGeneral(
            SUCCESS.USER_MAILBOX.CODE,
            SUCCESS.USER_MAILBOX.GET.GENERAL.CODE,
            SUCCESS.USER_MAILBOX.GET.GENERAL.MESSAGE),
        mailboxDeleteGeneral(
            SUCCESS.USER_MAILBOX.CODE,
            SUCCESS.USER_MAILBOX.DELETE.GENERAL.CODE,
            SUCCESS.USER_MAILBOX.DELETE.GENERAL.MESSAGE),
        mailboxCreateMailGeneral(
            SUCCESS.USER_MAILBOX.CODE,
            SUCCESS.USER_MAILBOX.CREATE.GENERAL.CODE,
            SUCCESS.USER_MAILBOX.CREATE.GENERAL.MESSAGE),
        mailboxUpdateMailGeneral(
            SUCCESS.USER_MAILBOX.CODE,
            SUCCESS.USER_MAILBOX.UPDATE_MAIL.GENERAL.CODE,
            SUCCESS.USER_MAILBOX.CREATE_MAIL.GENERAL.MESSAGE);;

        //==========================================================================================
        @JsonCreator
        static SuccessCode.SuccessType FindEnum(@JsonProperty("code") int code,
            @JsonProperty("subCode") int subCode) {

            switch(code) {

                case 0: {
                    return undefined;
                }

                case SUCCESS.GENERAL.CODE: {
                    switch (subCode){
                        case SUCCESS.GENERAL.SUB_CODE: {
                            return general;
                        }
                        default: {
                            Logger.warn("unable to find " + SuccessType.class.getName() + " during deserialization of [code = " + code + ", sub_code = " + subCode + "] for enum ");
                            Logger.warn(undefined.getClass().getName() + " returned when matching values to " + SuccessType.class.getName() + ". this is only returned because nothing else matched.");
                            return undefined;
                        }
                    }
                }

                case SUCCESS.REGISTRATION.CODE: {

                    switch(subCode){
                        case SUCCESS.REGISTRATION.GENERAL.CODE: {
                            return registration;
                        }
                        default: {
                            Logger.warn("unable to find " + SuccessType.class.getName() + " during deserialization of [code = " + code + ", sub_code = " + subCode + "] for enum ");
                            Logger.warn(undefined.getClass().getName() + " returned when matching values to " + SuccessType.class.getName() + ". this is only returned because nothing else matched.");
                            return undefined;
                        }
                    }

                }

                case SUCCESS.USER_SECURITY_INFORMATION.CODE: {

                    switch(subCode){
                        case SUCCESS.GENERAL.CODE: {
                            return userSecurityInformation;
                        }
                        default: {
                            Logger.warn("unable to find " + SuccessType.class.getName() + " during deserialization of [code = " + code + ", sub_code = " + subCode + "] for enum ");
                            Logger.warn(undefined.getClass().getName() + " returned when matching values to " + SuccessType.class.getName() + ". this is only returned because nothing else matched.");
                            return undefined;
                        }
                    }

                }

                case SUCCESS.USER_CHAT_SPACE.CODE: {

                    switch (subCode){
                        case SUCCESS.USER_CHAT_SPACE.GENERAL.CODE: {
                            return userChatSpace;
                        }

                        case SUCCESS.USER_CHAT_SPACE.GET.GENERAL.CODE: {
                            return getChatSpace;
                        }

                        case SUCCESS.USER_CHAT_SPACE.CREATE.GENERAL.CODE: {
                            return createChatSpace;
                        }

                        default: {
                            Logger.warn("unable to find " + SuccessType.class.getName() + " during deserialization of [code = " + code + ", sub_code = " + subCode + "] for enum ");
                            Logger.warn(undefined.getClass().getName() + " returned when matching values to " + SuccessType.class.getName() + ". this is only returned because nothing else matched.");
                            return undefined;
                        }
                    }
                }

                case USER_PROFILE.CODE: {
                    switch (subCode){
                        case SUCCESS.USER_PROFILE.GENERAL.CODE: {
                            return userProfileGeneral;
                        }
                        case SUCCESS.USER_PROFILE.CREATE.GENERAL.CODE: {
                            return userProfileCreateUserProfileGeneral;
                        }
                        case SUCCESS.USER_PROFILE.GET.GENERAL.CODE: {
                            return userProfileGetUserProfileGeneral;
                        }
                        case SUCCESS.USER_PROFILE.DELETE.GENERAL.CODE: {
                            return userProfileDeleteUserProfileGeneral;
                        }
                        case SUCCESS.USER_PROFILE.UPDATE.GENERAL.CODE: {
                            return userProfileUpdateUserProfileGeneral;
                        }
                        case SUCCESS.USER_PROFILE.CREATE_USER.GENERAL.CODE: {
                            return userProfileCreateUserGeneral;
                        }
                        case SUCCESS.USER_PROFILE.DELETE_USER.GENERAL.CODE: {
                            return userProfileDeleteUserGeneral;
                        }

                        default: {
                            Logger.warn("unable to find " + SuccessType.class.getName() + " during deserialization of [code = " + code + ", sub_code = " + subCode + "] for enum ");
                            Logger.warn(undefined.getClass().getName() + " returned when matching values to " + SuccessType.class.getName() + ". this is only returned because nothing else matched.");
                            return undefined;
                        }
                    }
                }

                case USER_MAILBOX.CODE: {
                    return undefined;
                }

                default: {
                    Logger.warn("unable to find " + SuccessType.class.getName() + " during deserialization of [code = " + code + ", sub_code = " + subCode + "] for enum ");
                    Logger.warn(undefined.getClass().getName() + " returned when matching values to " + SuccessType.class.getName() + ". this is only returned because nothing else matched.");
                    return undefined;
                }

            }
        }

        SuccessType(int code, int subCode, String message) {
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

    static class SUCCESS {

        static class GENERAL {
            static final int CODE = 1;
            static final int SUB_CODE = 1;
            static final String MESSAGE = "The operation has completed successfully.";
        }

        static class REGISTRATION{
            static final int CODE = 2;
            static class GENERAL {
                static final int CODE = 1;
                static final String MESSAGE = "Registration has been completed successfully";
            }
        }

        static class USER_SECURITY_INFORMATION {
            static final int CODE = 3;
            static class GENERAL {
                static final int CODE = 1;
                static final String MESSAGE = "User security information has been completed successfully.";
            }

        }

        static class USER_CHAT_SPACE {

            static final int CODE = 4;

            static class GENERAL {
                static final int CODE = 1;
                static final String MESSAGE = "User chat space operation has been completed successfully.";
            }

            static class GET {
                static class GENERAL {
                    static final int CODE = 2;
                    static final String MESSAGE = "Get user chat space operation has been completed successfully.";
                }
            }

            static class CREATE {
                static class GENERAL {
                    static final int CODE = 3;
                    static final String MESSAGE = "Create user chat space operation has been completed successfully.";
                }
            }
        }

        static class USER_PROFILE{

            static final int CODE = 5;

            static class GENERAL {
                static final int CODE = 1;
                static final String MESSAGE = "User profile operation has been completed successfully.";
            }

            static class GET {
                static class GENERAL {
                    static final int CODE = 2;
                    static final String MESSAGE = "Get user profile operation has been completed successfully.";
                }
            }

            static class CREATE {
                static class GENERAL {
                    static final int CODE = 3;
                    static final String MESSAGE = "Create user profile operation has been completed successfully.";
                }
            }

            static class DELETE {
                static class GENERAL {
                    static final int CODE = 4;
                    static final String MESSAGE = "Delete user profile operation has been completed successfully.";
                }
            }

            static class UPDATE {
                static class GENERAL {
                    static final int CODE = 5;
                    static final String MESSAGE = "Update user profile operation has been completed successfully.";
                }
            }

            static class CREATE_USER {
                static class GENERAL {
                    static final int CODE = 6;
                    static final String MESSAGE = "Create user operation has been completed successfully.";
                }
            }

            static class DELETE_USER {
                static class GENERAL {
                    static final int CODE = 7;
                    static final String MESSAGE = "Delete user profile operation has been completed successfully.";
                }
            }

        }

        static class USER_MAILBOX{

            static final int CODE = 6;

            static class GENERAL {
                static final int CODE = 1;
                static final String MESSAGE = "User mailbox operation has been completed successfully.";
            }

            static class GET {
                static class GENERAL {
                    static final int CODE = 2;
                    static final String MESSAGE = "Get user mailbox operation has been completed successfully.";
                }
            }

            static class CREATE {
                static class GENERAL {
                    static final int CODE = 3;
                    static final String MESSAGE = "Create user mailbox operation has been completed successfully.";
                }
            }

            static class DELETE {
                static class GENERAL {
                    static final int CODE = 4;
                    static final String MESSAGE = "Delete user mailbox operation has been completed successfully.";
                }
            }

            static class UPDATE {
                static class GENERAL {
                    static final int CODE = 5;
                    static final String MESSAGE = "Update user mailbox operation has been completed successfully.";
                }
            }

            static class CREATE_MAILBOX {
                static class GENERAL {
                    static final int CODE = 6;
                    static final String MESSAGE = "Create mailbox operation has been completed successfully.";
                }
            }

            static class DELETE_MAILBOX {
                static class GENERAL {
                    static final int CODE = 7;
                    static final String MESSAGE = "Delete user mailbox operation has been completed successfully.";
                }
            }

            static class CREATE_MAIL {
                static class GENERAL {
                    static final int CODE = 8;
                    static final String MESSAGE = "Create mail operation has been completed successfully.";
                }
            }

            static class UPDATE_MAIL {
                static class GENERAL {
                    static final int CODE = 8;
                    static final String MESSAGE = "Update mail operation has been completed successfully.";
                }
            }
        }
    }
}
