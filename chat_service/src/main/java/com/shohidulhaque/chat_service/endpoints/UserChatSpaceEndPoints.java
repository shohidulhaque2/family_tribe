package com.shohidulhaque.chat_service.endpoints;

import com.shohidulhaque.chat_service.data_transfer_object.validation.ChatSpaceDtoArgumentViolationResponse;
import com.shohidulhaque.chat_service.mapper.ChatSpaceDataModelToChatSpaceMapper;
import com.shohidulhaque.chat_service.repository.ChatSpaceUserRepository;
import com.shohidulhaque.chat_service.service.ChatSpaceService;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceDtoResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.CreateChatSpaceDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.CreateChatSpaceUserDtoRequest;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/chat-space-service")
@RestController
public class UserChatSpaceEndPoints {

    static Logger Logger = LoggerFactory.getLogger(UserChatSpaceEndPoints.class);

    Validator validator;

    javax.validation.Validator javaxValidator;
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
    ChatSpaceService chatSpaceService;
    //==============================================================================================
    ChatSpaceDataModelToChatSpaceMapper chatSpaceDataModelToChatSpaceMapper;
    //==============================================================================================
    ChatSpaceUserRepository chatSpaceUserRepository;
    //==============================================================================================
    @Autowired
    public UserChatSpaceEndPoints(
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
    @PostMapping("/chatspaces")
    public ResponseEntity<ChatSpaceDtoResponse> addChatSpaceUser(
        @RequestBody CreateChatSpaceUserDtoRequest createChatSpaceUserDtoRequest){
        ChatSpaceDtoResponse chatSpaceDtoResponse = this.chatSpaceService.addNewChatSpaceUser(createChatSpaceUserDtoRequest);
        chatSpaceDtoResponse.add(WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(UserChatSpaceEndPoints.class)
                .addChatSpaceUser(createChatSpaceUserDtoRequest))
            .withSelfRel());
        return ResponseEntity
            .status(chatSpaceDtoResponse.getContent().getResponseType().getCode())
            .body(chatSpaceDtoResponse);
    }
    //==============================================================================================
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/chatspaces/{userId}")
    public ResponseEntity<ChatSpaceDtoResponse> addChatSpace(
        @PathVariable("userId") UUID userUuid,
        @RequestBody CreateChatSpaceDtoRequest createChatSpaceDtoRequest) {
        ChatSpaceDtoResponse chatSpaceDtoResponse = this.chatSpaceService.addChatSpace(userUuid,  createChatSpaceDtoRequest);
        chatSpaceDtoResponse.add(WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(UserChatSpaceEndPoints.class)
                .addChatSpace(userUuid, createChatSpaceDtoRequest))
            .withSelfRel());
        return ResponseEntity
            .status(chatSpaceDtoResponse.getContent().getResponseType().getCode())
            .body(chatSpaceDtoResponse);
    }
    //==============================================================================================
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/chatspaces/{userUuid}")
    public ResponseEntity<ChatSpaceDtoResponse> getChatSpaceUser(
        @PathVariable("userUuid") UUID userUuid) {
        ChatSpaceDtoResponse chatSpaceDtoResponse = this.chatSpaceService.getChatSpaceUser(userUuid);
        chatSpaceDtoResponse.add(WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(UserChatSpaceEndPoints.class)
                .getChatSpaceUser(userUuid))
            .withSelfRel());
        return ResponseEntity
            .status(chatSpaceDtoResponse.getContent().getResponseType().getCode())
            .body(chatSpaceDtoResponse);
    }
    //==============================================================================================
}
