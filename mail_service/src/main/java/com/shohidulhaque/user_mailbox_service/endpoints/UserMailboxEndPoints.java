package com.shohidulhaque.user_mailbox_service.endpoints;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.CreateUserMailboxDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.FromUserMailDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailUpdateReadStatusDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailboxDtoResponse;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.Undefined;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import com.shohidulhaque.user_mailbox_service.data_transfer_object.validation.MailBoxDtoArgumentViolationResponse;
import com.shohidulhaque.user_mailbox_service.mapper.UserMailboxMapper;
import com.shohidulhaque.user_mailbox_service.service.UserMailboxService;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RequestMapping("/api/v1/user-mailbox-service")
@RestController
public class UserMailboxEndPoints {
    static org.slf4j.Logger Logger = LoggerFactory.getLogger(UserMailboxEndPoints.class);
    //==============================================================================================
    //general exception handlers
    @ExceptionHandler({Exception.class})
    public ResponseEntity<UserMailboxDtoResponse> handleException(Exception e) {
        Logger.warn("exception handled by general error handling code.", e);
        ResponseType errorResponseType = new ResponseType(
            HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Undefined.undefined.subCode);
        UserMailboxDtoResponse userMailboxDtoResponse = MailBoxDtoArgumentViolationResponse
            .GetResponseBodyForError(errorResponseType,ErrorType.userMailboxGeneral);
        return ResponseEntity.status(userMailboxDtoResponse.getContent().getResponseType().getCode()).body(userMailboxDtoResponse);
    }
    //==============================================================================================
    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<UserMailboxDtoResponse> handleAllDataAccessException(
        DataAccessException e) {
        Logger.warn("data access exception", e);
        ResponseType errorResponseType = new ResponseType(
            HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Undefined.undefined.subCode);
        UserMailboxDtoResponse userMailboxDtoResponse = MailBoxDtoArgumentViolationResponse
            .GetResponseBodyForError(errorResponseType,ErrorType.userMailboxGeneral);
        return ResponseEntity.status(userMailboxDtoResponse.getContent().getResponseType().getCode()).body(userMailboxDtoResponse);
    }
    //==============================================================================================
    UserMailboxService userMailboxService;
    UserMailboxMapper userMailboxMapper;
    Validator validator;
    javax.validation.Validator javaxValidator;
    //==============================================================================================
    @Autowired
    public UserMailboxEndPoints(
        UserMailboxService userMailboxService,
        Validator validator,
        javax.validation.Validator javaxValidator,
        UserMailboxMapper userMailboxMapper) {
        this.userMailboxService = userMailboxService;
        this.validator = validator;
        this.javaxValidator = javaxValidator;
        this.userMailboxMapper = userMailboxMapper;
    }
    //==============================================================================================
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/users/mailbox/{userMailboxUuid}")
    public ResponseEntity<UserMailboxDtoResponse> getMailbox(
        @PathVariable("userMailboxUuid") UUID userMailboxUuid) {
     UserMailboxDtoResponse userMailboxDtoResponse = this.userMailboxService.getUserMailbox(userMailboxUuid);
     userMailboxDtoResponse.add(WebMvcLinkBuilder
         .linkTo(WebMvcLinkBuilder
             .methodOn(UserMailboxEndPoints.class)
             .getMailbox(userMailboxUuid))
         .withSelfRel());
     return ResponseEntity
         .status(userMailboxDtoResponse.getContent().getResponseType().getCode())
         .body(userMailboxDtoResponse);
    }
    //==============================================================================================
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/users/mailbox/{userUuid}/mail/{mailUuid}")
    public ResponseEntity<UserMailboxDtoResponse> updateMail(
        @PathVariable("userUuid") UUID userUuid,
        @PathVariable("mailUuid") UUID mailUuid,
        @RequestBody UserMailUpdateReadStatusDtoRequest userMailUpdateReadStatusDtoRequest){
        UserMailboxDtoResponse userMailboxDtoResponse = this.userMailboxService.mailReadStatus(userUuid, mailUuid, userMailUpdateReadStatusDtoRequest);
        userMailboxDtoResponse.add(WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(UserMailboxEndPoints.class)
                .updateMail(userUuid, mailUuid, userMailUpdateReadStatusDtoRequest))
            .withSelfRel());
        return ResponseEntity
            .status(userMailboxDtoResponse.getContent().getResponseType().getCode())
            .body(userMailboxDtoResponse);
    }
    //==============================================================================================
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/users/mailbox/{recipientUserUuid}/mail")
    public ResponseEntity<UserMailboxDtoResponse> saveMail(
        @PathVariable("recipientUserUuid") UUID recipientUserUuid,
        @RequestBody FromUserMailDtoRequest fromUserMailDtoRequest){
        UserMailboxDtoResponse userMailboxDtoResponse =  this.userMailboxService.saveMail(recipientUserUuid, fromUserMailDtoRequest);
        userMailboxDtoResponse.add(WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(UserMailboxEndPoints.class)
                .saveMail(recipientUserUuid, fromUserMailDtoRequest))
            .withSelfRel());
        return ResponseEntity
            .status(userMailboxDtoResponse.getContent().getResponseType().getCode())
            .body(userMailboxDtoResponse);
    }
    //==============================================================================================
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/users/mailbox")
    public ResponseEntity<UserMailboxDtoResponse> createMailbox(@RequestBody CreateUserMailboxDtoRequest createUserMailboxDtoRequest) {
        UserMailboxDtoResponse userMailboxDtoResponse = this.userMailboxService.createUserMailbox(createUserMailboxDtoRequest);
        userMailboxDtoResponse.add(WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(UserMailboxEndPoints.class)
                .createMailbox(createUserMailboxDtoRequest))
            .withSelfRel());
        return ResponseEntity
            .status(userMailboxDtoResponse.getContent().getResponseType().getCode())
            .body(userMailboxDtoResponse);
    }
    //==============================================================================================
}
