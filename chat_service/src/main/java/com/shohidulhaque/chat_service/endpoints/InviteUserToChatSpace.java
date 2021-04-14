package com.shohidulhaque.chat_service.endpoints;

import com.shohidulhaque.chat_service.data_transfer_object.validation.ChatSpaceDtoArgumentViolationResponse;
import com.shohidulhaque.chat_service.mapper.ChatSpaceDataModelToChatSpaceMapper;
import com.shohidulhaque.chat_service.repository.ChatSpaceUserRepository;
import com.shohidulhaque.chat_service.service.ChatSpaceService;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceDtoResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceInvitationDtoRequest;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.Undefined;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/chat-space-service")
@RestController
public class InviteUserToChatSpace {
    //==============================================================================================
    Logger Logger = LoggerFactory.getLogger(InviteUserToChatSpace.class);
    //==============================================================================================
    //general exception handlers
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ChatSpaceDtoResponse> handleException(Exception e) {
        Logger.warn("exception handled by general error handling code.", e);
        ResponseType errorResponseType = new ResponseType(
            HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Undefined.undefined.subCode);
        ChatSpaceDtoResponse chatSpaceDtoResponse = ChatSpaceDtoArgumentViolationResponse
            .GetResponseBodyForError(errorResponseType,ErrorType.generalChatSpaceError);
        return ResponseEntity.status(chatSpaceDtoResponse.getContent().getResponseType().getCode()).body(chatSpaceDtoResponse);
    }
    //==============================================================================================
    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<ChatSpaceDtoResponse> handleAllDataAccessException(
        DataAccessException e) {
        Logger.warn("data access exception", e);
        ResponseType errorResponseType = new ResponseType(
            HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Undefined.undefined.subCode);
        ChatSpaceDtoResponse chatSpaceDtoResponse = ChatSpaceDtoArgumentViolationResponse.GetResponseBodyForError(errorResponseType,ErrorType.generalChatSpaceError);
        return ResponseEntity.status(chatSpaceDtoResponse.getContent().getResponseType().getCode()).body(chatSpaceDtoResponse);
    }
    //==============================================================================================
    Validator validator;
    //==============================================================================================
    javax.validation.Validator javaxValidator;
    //==============================================================================================
    ChatSpaceService chatSpaceService;
    //==============================================================================================
    ChatSpaceDataModelToChatSpaceMapper chatSpaceDataModelToChatSpaceMapper;
    //==============================================================================================
    ChatSpaceUserRepository chatSpaceUserRepository;
    //==============================================================================================
    @Autowired
    public InviteUserToChatSpace(
        ChatSpaceUserRepository chatSpaceUserRepository,
        ChatSpaceService chatSpaceService,
        ChatSpaceDataModelToChatSpaceMapper chatSpaceDataModelToChatSpaceMapper,
        Validator validator,
        javax.validation.Validator javaxValidator) {
        this.chatSpaceService = chatSpaceService;
        this.chatSpaceDataModelToChatSpaceMapper = chatSpaceDataModelToChatSpaceMapper;
        this.chatSpaceUserRepository = chatSpaceUserRepository;
        this.validator = validator;
        this.javaxValidator = javaxValidator;
    }
    //==============================================================================================
    //TODO: change this /{uuid}/chatspaces/{chatpsace_uuid}/members
    @PostMapping("/users/{uuid}/chatspaces/members")
    public ResponseEntity<ChatSpaceDtoResponse> inviteChatSpaceMember(
        @PathVariable("uuid") UUID uuid,
        @RequestBody ChatSpaceInvitationDtoRequest chatSpaceInvitationDtoRequest) {
        ChatSpaceDtoResponse chatSpaceDtoResponse = this.chatSpaceService.createInvitation(uuid, chatSpaceInvitationDtoRequest);
        chatSpaceDtoResponse.add(WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(InviteUserToChatSpace.class)
                .inviteChatSpaceMember(uuid, chatSpaceInvitationDtoRequest))
            .withSelfRel());
        return ResponseEntity
            .status(chatSpaceDtoResponse.getContent().getResponseType().getCode())
            .body(chatSpaceDtoResponse);
    }
    //==============================================================================================
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/users/{chatSpaceUserUuid}/chatspaces/{chatSpaceUuid}/invitation/{invitationUuid}/person/{uuidOfInvitee}")
    public ResponseEntity<ChatSpaceDtoResponse>  acceptInvitation(
        @PathVariable("chatSpaceUserUuid") UUID chatSpaceUserUuid,
        @PathVariable("chatSpaceUuid") UUID chatSpaceUuid,
        @PathVariable("invitationUuid") UUID invitationUuid,
        @PathVariable("uuidOfInvitee") UUID uuidOfInvitee){
        ChatSpaceDtoResponse chatSpaceDtoResponse = this.chatSpaceService.acceptInvitation(chatSpaceUserUuid, chatSpaceUuid, invitationUuid, uuidOfInvitee);
        chatSpaceDtoResponse.add(WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(InviteUserToChatSpace.class)
                .acceptInvitation(chatSpaceUserUuid, chatSpaceUuid, invitationUuid, uuidOfInvitee))
            .withSelfRel());
        return ResponseEntity
            .status(chatSpaceDtoResponse.getContent().getResponseType().getCode())
            .body(chatSpaceDtoResponse);
    }
    //==============================================================================================
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{userId}/chatspaces/people")
    public ResponseEntity<Void> addPersonToChatSpace(@PathVariable("userId") String userId,
        @RequestBody ChatSpaceDtoRequest chatSpaceDtoRequest) {
        //validate user request
        //make an attempt to add a new user
        //send an invitation as an email and add to the users inbox
        return null;
    }
    //==============================================================================================
}
