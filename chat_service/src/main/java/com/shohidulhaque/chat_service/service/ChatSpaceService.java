package com.shohidulhaque.chat_service.service;

import com.shohidulhaque.chat_service.data_model.ChatSpace;
import com.shohidulhaque.chat_service.data_model.ChatSpaceUser;
import com.shohidulhaque.chat_service.data_model.Member;
import com.shohidulhaque.chat_service.data_model.Message;
import com.shohidulhaque.chat_service.data_model.UserInvitation;
import com.shohidulhaque.chat_service.data_transfer_object.validation.ChatSpaceDtoArgumentViolationResponse;
import com.shohidulhaque.chat_service.mapper.ChapSpaceInvitationMapper;
import com.shohidulhaque.chat_service.mapper.ChatSpaceDataModelToChatSpaceMapper;
import com.shohidulhaque.chat_service.mapper.ChatSpaceUserMapper;
import com.shohidulhaque.chat_service.mapper.MemberInvitationMapper;
import com.shohidulhaque.chat_service.mapper.NewMessageMapper;
import com.shohidulhaque.chat_service.repository.ChatSpaceRepository;
import com.shohidulhaque.chat_service.repository.ChatSpaceUserRepository;
import com.shohidulhaque.chat_service.repository.MessageRepository;
import com.shohidulhaque.chat_service.repository.UserInvitationRepository;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceDtoResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceInvitation;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceInvitationDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceMessageRequestDto;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ContentChatSpaceSuccessResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.CreateChatSpaceDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.CreateChatSpaceUserDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.MemberAcceptance;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.NewMessage;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.success_codes.SuccessCode.SuccessType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatSpaceService {
//    .type(ErrorType.createChatSpaceGeneralError)
//    .type(ErrorType.createChatSpaceBadArguments)
//    .type(ErrorType.createChatSpaceBadArguments)
//    .type(ErrorType.createChatSpaceNameAlreadyExist)
//    .type(ErrorType.createChatSpaceGeneralError)
    //==============================================================================================
    static Logger Logger = LoggerFactory.getLogger(ChatSpaceService.class);
    //==============================================================================================
    private final ChapSpaceInvitationMapper chatSpaceInvitationMapper;
    //==============================================================================================
    ChatSpaceUserRepository chatSpaceUserRepository;
    //==============================================================================================
    ServiceProxy serviceProxy;
    //==============================================================================================
    ChatSpaceRepository chatSpaceRepository;
    //==============================================================================================
    UserInvitationRepository userInvitationRepository;
    //==============================================================================================
    MessageRepository messageRepository;
    //==============================================================================================
    Clock clock;
    //==============================================================================================
    Validator javaxValidator;
    //==============================================================================================
    ChatSpaceUserMapper chatSpaceUserMapper;
    //==============================================================================================
    ChatSpaceDataModelToChatSpaceMapper chatSpaceDataModelToChatSpaceMapper;
    //==============================================================================================
    MemberInvitationMapper memberInvitationMapper;
    //==============================================================================================
    NewMessageMapper newMessageMapper;
    //==============================================================================================
    @Autowired
    public ChatSpaceService(
        ChatSpaceUserRepository chatSpaceUserRepository,
        ChatSpaceRepository chatSpaceRepository,
        Validator javaxValidator,
        UserInvitationRepository userInvitationRepository,
        ServiceProxy serviceProxy,
        ChapSpaceInvitationMapper chatSpaceInvitationMapper,
        ChatSpaceUserMapper chatSpaceUserMapper,
        ChatSpaceDataModelToChatSpaceMapper chatSpaceDataModelToChatSpaceMapper,
        MemberInvitationMapper memberInvitationMapper,
        NewMessageMapper newMessageMapper,
        MessageRepository messageRepository,
        Clock clock) {
        this.chatSpaceUserRepository = chatSpaceUserRepository;
        this.javaxValidator = javaxValidator;
        this.chatSpaceRepository = chatSpaceRepository;
        this.serviceProxy = serviceProxy;
        this.userInvitationRepository = userInvitationRepository;
        this.clock = clock;
        this.chatSpaceInvitationMapper = chatSpaceInvitationMapper;
        this.chatSpaceUserMapper = chatSpaceUserMapper;
        this.chatSpaceDataModelToChatSpaceMapper = chatSpaceDataModelToChatSpaceMapper;
        this.memberInvitationMapper = memberInvitationMapper;
        this.newMessageMapper = newMessageMapper;
        this.messageRepository = messageRepository;
    }
    //==============================================================================================
    public  Optional<ChatSpaceUser> doesUserExist(UUID userUuid) throws DataAccessException {
        return this.chatSpaceUserRepository.getChatSpaceUser(userUuid);
    }
    //==============================================================================================
    public ChatSpaceDtoResponse addNewChatSpaceUser(
        CreateChatSpaceUserDtoRequest createChatSpaceUserDtoRequest){
        Set<ConstraintViolation<ChatSpaceDtoRequest>> violations = this.javaxValidator.validate(createChatSpaceUserDtoRequest, Default.class);
        if(!violations.isEmpty()){
            return ChatSpaceDtoArgumentViolationResponse.BadArgumentException(createChatSpaceUserDtoRequest, violations,  ErrorType.generalChatSpaceError);
        }
        Optional<ChatSpaceUser> chatSpaceUserOptional = this.chatSpaceUserRepository
            .getChatSpaceUser(createChatSpaceUserDtoRequest.getUuid());
        if(chatSpaceUserOptional.isPresent()){
            ResponseType responseType = ResponseType.GetConflictResponseType();
            return ChatSpaceDtoArgumentViolationResponse.GetResponseBodyForError( responseType, ErrorType.createChatSpaceGeneralError);
        }
        ChatSpaceUser chatSpaceUser = ChatSpaceUser.builder()
                .creatorUuid(createChatSpaceUserDtoRequest.getUuid()).
                uuid(UUID.randomUUID())
                .build();
        chatSpaceUser = this.chatSpaceUserRepository.save(chatSpaceUser);
        return chatSpaceUserIsCreatedSuccessfully(chatSpaceUser);
    }
    public ChatSpaceDtoResponse chatSpaceUserIsCreatedSuccessfully(ChatSpaceUser chatSpaceUserDataModel){
        com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceUser chatSpaceUser = this.chatSpaceUserMapper.to(chatSpaceUserDataModel);
        ResponseType responseType = ResponseType.GetCreatedResponseType();
        ContentChatSpaceSuccessResponse contentChatSpaceSuccessResponse = new ContentChatSpaceSuccessResponse(SuccessType.createChatSpace, chatSpaceUser);
        ChatSpaceDtoResponse chatSpaceDtoResponse = ChatSpaceDtoResponse.SuccessChatSpaceDtoResponse(responseType, List.of(contentChatSpaceSuccessResponse));
        return chatSpaceDtoResponse;
    }
    //==============================================================================================
    public Optional<ChatSpace> doesChatSpaceNameExist(UUID userUuid, String chatSpaceName) throws DataAccessException {
        return  this.chatSpaceUserRepository.getChatSpace(userUuid, chatSpaceName);
    }
    //==============================================================================================
    public ChatSpaceDtoResponse addChatSpace(UUID userUuid, CreateChatSpaceDtoRequest createChatSpaceDtoRequest) {
        Set<ConstraintViolation<ChatSpaceDtoRequest>> violations = this.javaxValidator.validate(createChatSpaceDtoRequest);
        if(!violations.isEmpty()){
            return ChatSpaceDtoArgumentViolationResponse.BadArgumentException(createChatSpaceDtoRequest, violations,  ErrorType.generalChatSpaceError);
        }

        Optional<ChatSpaceUser> chatSpaceUserOptional = this.chatSpaceUserRepository.getChatSpaceUser(userUuid);
        if(chatSpaceUserOptional.isEmpty()){
            ResponseType responseType = ResponseType.GetBadRequestResponseType();
            return ChatSpaceDtoArgumentViolationResponse.GetResponseBodyForError( responseType, ErrorType.createChatSpaceGeneralError);
        }

        Optional<ChatSpace> chatSpaceOptional = this.chatSpaceRepository.findByName(createChatSpaceDtoRequest.getChatSpaceName());
        if(chatSpaceOptional.isPresent()){
            ResponseType responseType = ResponseType.GetConflictResponseType();
            return ChatSpaceDtoArgumentViolationResponse.GetResponseBodyForError( responseType, ErrorType.createChatSpaceGeneralError);
        }

        List<ChatSpace> chatSpaceList = chatSpaceUserOptional.get().getChatSpace();
        ChatSpace chatSpace = ChatSpace.builder()
                                    .name(createChatSpaceDtoRequest.getChatSpaceName())
                                    .uuid(UUID.randomUUID())
                                    .build();
        chatSpaceList.add(chatSpace);
        return chatSpaceIsAddedSuccessfully(chatSpace);
    }
    private ChatSpaceDtoResponse chatSpaceIsAddedSuccessfully(ChatSpace chatSpaceDataModel){
        com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpace chatSpace = this.chatSpaceDataModelToChatSpaceMapper
            .chatSpaceDataModelToChatSpace(chatSpaceDataModel);
        ContentChatSpaceSuccessResponse contentChatSpaceSuccessResponse = new ContentChatSpaceSuccessResponse(SuccessType.userChatSpace, chatSpace);
        ResponseType responseType = ResponseType.GetCreatedResponseType();
        ChatSpaceDtoResponse chatSpaceDtoResponse = ChatSpaceDtoResponse.SuccessChatSpaceDtoResponse(responseType, List.of(contentChatSpaceSuccessResponse));
        return chatSpaceDtoResponse;
    }
    //==============================================================================================
    public ChatSpaceDtoResponse getChatSpaceUser(UUID userUuid) throws DataAccessException {
        Optional<ChatSpaceUser> chatSpaceUserOptional = this.chatSpaceUserRepository
            .getChatSpaceUser(userUuid);
        if(chatSpaceUserOptional.isEmpty()){
            ResponseType responseType = ResponseType.GetBadRequestResponseType();
            ChatSpaceDtoResponse chatSpaceDtoResponse = ChatSpaceDtoArgumentViolationResponse
                .GetResponseBodyForError(responseType, ErrorType.generalChatSpaceError);
            return chatSpaceDtoResponse;
        }
        return chatSpaceUserRetrievedSuccessfully(chatSpaceUserOptional.get());
    }
    private ChatSpaceDtoResponse chatSpaceUserRetrievedSuccessfully(ChatSpaceUser chatSpaceUserDataModel){
        com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceUser chatSpaceUser = this.chatSpaceUserMapper
            .to(chatSpaceUserDataModel);
        ContentChatSpaceSuccessResponse contentChatSpaceSuccessResponse = new ContentChatSpaceSuccessResponse(SuccessType.userChatSpace, chatSpaceUser);
        ResponseType responseType = ResponseType.GetOkResponseType();
        ChatSpaceDtoResponse chatSpaceDtoResponse = ChatSpaceDtoResponse.SuccessChatSpaceDtoResponse(responseType, List.of(contentChatSpaceSuccessResponse));
        return chatSpaceDtoResponse;
    }
    //==============================================================================================
    public ChatSpaceDtoResponse createInvitation(UUID chatSpaceUserUuid, ChatSpaceInvitationDtoRequest chatSpaceInvitationDtoRequest) {
        Set<ConstraintViolation<ChatSpaceDtoRequest>> violations = this.javaxValidator.validate(chatSpaceInvitationDtoRequest, Default.class);
        if (!violations.isEmpty()) {
            return ChatSpaceDtoArgumentViolationResponse.BadArgumentException(chatSpaceInvitationDtoRequest, violations,  ErrorType.generalChatSpaceError);
        }
        Optional<ChatSpaceUser> chatSpaceUserOptional = this.chatSpaceUserRepository.getChatSpaceUser(chatSpaceUserUuid);
        if(chatSpaceUserOptional.isEmpty()){
            return ChatSpaceDtoArgumentViolationResponse.GetResponseBodyForError(ResponseType.GetBadRequestResponseType(),  ErrorType.generalChatSpaceError);
        }
        Optional<ChatSpace> chatSpaceOptional = this.chatSpaceUserRepository.getChatSpace(chatSpaceUserUuid, chatSpaceInvitationDtoRequest.getChatSpaceUuid());
        if(chatSpaceOptional.isEmpty()){
            return ChatSpaceDtoArgumentViolationResponse.GetResponseBodyForError(ResponseType.GetBadRequestResponseType(),  ErrorType.generalChatSpaceError);
        }
        ChatSpace chatSpace = chatSpaceOptional.get();
        Optional<UUID> invitedUserUuidOptional;
        try {
            invitedUserUuidOptional = this.serviceProxy.doesMemberExist(chatSpaceInvitationDtoRequest.getEmail());
        }
        catch(IOException e){
            return ChatSpaceDtoArgumentViolationResponse.GetResponseBodyForError(ResponseType.GetInternalServerResponseType(), ErrorType.generalChatSpaceError);
        }

        if(invitedUserUuidOptional.isEmpty()){
            return ChatSpaceDtoArgumentViolationResponse.GetResponseBodyForError(ResponseType.GetBadRequestResponseType(), ErrorType.generalChatSpaceError);
        }

        UUID uuidOfInvitedPerson = invitedUserUuidOptional.get();

        UserInvitation userInvitation = UserInvitation
            .builder()
            .uuid(UUID.randomUUID())
            .creationTimestamp(Instant.now(this.clock))
            .expirationTimestamp(Instant.now(this.clock).plus(7, ChronoUnit.DAYS))
            .invitedUser(uuidOfInvitedPerson)
            .build();

        userInvitation = chatSpace.addUserInvitation(userInvitation);
        try{
            this.serviceProxy.sendInvitationToUser(uuidOfInvitedPerson, userInvitation);
        } catch (IOException e){
            chatSpace.removeInvitation(userInvitation);
            return ChatSpaceDtoArgumentViolationResponse.GetResponseBodyForError(ResponseType.GetInternalServerResponseType(), ErrorType.generalChatSpaceError);
        }
        return personHasBeenInvitedSuccessfully(userInvitation);
    }
    private ChatSpaceDtoResponse personHasBeenInvitedSuccessfully(UserInvitation userInvitation){
        ChatSpaceInvitation chatSpaceInvitation = chatSpaceInvitationMapper.to(userInvitation);
        ContentChatSpaceSuccessResponse contentChatSpaceSuccessResponse = new ContentChatSpaceSuccessResponse(SuccessType.userChatSpace, chatSpaceInvitation);
        ResponseType responseType = ResponseType.GetCreatedResponseType();
        ChatSpaceDtoResponse chatSpaceDtoResponse = ChatSpaceDtoResponse.SuccessChatSpaceDtoResponse(responseType, List.of(contentChatSpaceSuccessResponse));
        return chatSpaceDtoResponse;
    }
    //==============================================================================================
    public  ChatSpaceDtoResponse acceptInvitation(UUID chatSpaceUserUuid, UUID chatSpaceUuid,  UUID uuidOfExistingInvitation, UUID inviteeUuid){
        Optional<UserInvitation> userInvitationOptional = this.chatSpaceUserRepository.getInvitation(chatSpaceUserUuid, inviteeUuid, uuidOfExistingInvitation);
        if(userInvitationOptional.isEmpty()){
            return ChatSpaceDtoArgumentViolationResponse.GetResponseBodyForError(ResponseType.GetBadRequestResponseType(),  ErrorType.generalChatSpaceError);
        }
        UserInvitation userInvitation = userInvitationOptional.get();
        userInvitation.setInvitationAccepted(true);
        ChatSpace chatSpace = userInvitation.getChatSpace();
        Member member = Member.builder().memberUuid(inviteeUuid).uuid(UUID.randomUUID()).build();
        chatSpace.addMember(member);
        return personHasAcceptedInvitationAndAddedAsAMemberToTheChatSpace(member, chatSpace);
    }
    private ChatSpaceDtoResponse personHasAcceptedInvitationAndAddedAsAMemberToTheChatSpace(Member member, ChatSpace chatSpace){
        MemberAcceptance memberAcceptance = this.memberInvitationMapper.to(member);
        memberAcceptance.setChatSpaceUuid(chatSpace.getUuid());
        ContentChatSpaceSuccessResponse contentChatSpaceSuccessResponse = new ContentChatSpaceSuccessResponse(SuccessType.userChatSpace, memberAcceptance);
        ResponseType responseType = ResponseType.GetCreatedResponseType();
        ChatSpaceDtoResponse chatSpaceDtoResponse = ChatSpaceDtoResponse.SuccessChatSpaceDtoResponse(responseType, List.of(contentChatSpaceSuccessResponse));
        return chatSpaceDtoResponse;
    }
    //==============================================================================================
    public ChatSpaceDtoResponse addMessage(UUID chatSpaceCreator, UUID chatSpaceUuid, ChatSpaceMessageRequestDto chatSpaceMessageRequestDto){
        Set<ConstraintViolation<ChatSpaceDtoRequest>> violations = this.javaxValidator.validate(
            chatSpaceMessageRequestDto);
        if (!violations.isEmpty()) {
            return ChatSpaceDtoArgumentViolationResponse.BadArgumentException(
                chatSpaceMessageRequestDto, violations,  ErrorType.generalChatSpaceError);
        }
        Optional<ChatSpace> chatSpaceOptional = this.chatSpaceRepository.findChatSpace(
            chatSpaceCreator, chatSpaceUuid, chatSpaceMessageRequestDto.getUuidOfMember());
        if(chatSpaceOptional.isEmpty()){
            return ChatSpaceDtoArgumentViolationResponse.BadArgumentException(
                chatSpaceMessageRequestDto, violations,  ErrorType.generalChatSpaceError);
        }
        ChatSpace chatSpace = chatSpaceOptional.get();
        Set<Message> messages = chatSpace.getMessages();
        Message message = Message.builder()
                            .message(chatSpaceMessageRequestDto.getMessage())
                            .fromUuid(chatSpaceMessageRequestDto.getUuidOfMember())
                            .uuid(UUID.randomUUID())
                            .createdTimestamp(this.clock.instant())
                            .build();

        //issue with mysql. need to save first for mysql, works fine with h2.
        message = this.messageRepository.save(message);
        messages.add(message);
        return messageAddedSuccessfully(message);
    }

    private ChatSpaceDtoResponse messageAddedSuccessfully(Message message) {
        NewMessage newMessage = this.newMessageMapper.to(message);
        ContentChatSpaceSuccessResponse contentChatSpaceSuccessResponse = new ContentChatSpaceSuccessResponse(SuccessType.userChatSpace, newMessage);
        ResponseType responseType = ResponseType.GetCreatedResponseType();
        ChatSpaceDtoResponse chatSpaceDtoResponse = ChatSpaceDtoResponse.SuccessChatSpaceDtoResponse(responseType, List.of(contentChatSpaceSuccessResponse));
        return chatSpaceDtoResponse;
    }

    //==============================================================================================
    //TODO: implement
    public boolean rejectInvitation(UUID chatSpaceUserUuid, UUID inviteeUuid, UUID uuidOfExistingInvitation){
//        List<UserInvitation> invitationList = this.chatSpaceUserRepository
//            .getInvitation(chatSpaceUserUuid, inviteeUuid, uuidOfExistingInvitation);
//        if(invitationList.size() == 1){
//            UserInvitation userInvitation = invitationList.get(0);
//            userInvitation.setInvitationAccepted(false);
//            //this.userInvitationRepository.save(userInvitation);
//            return true;
//        }
        return false;
    }
    //==============================================================================================
    //TODO: implement
    public boolean deleteInvitation(UUID chatSpaceUserUuid, UUID inviteeUuid, UUID uuidOfExistingInvitation) {
//        List<UserInvitation> invitationList = this.chatSpaceUserRepository.getInvitation(chatSpaceUserUuid, inviteeUuid, uuidOfExistingInvitation);
//        if(invitationList.size() == 1){
//            UserInvitation userInvitation = invitationList.get(0);
//            userInvitation.setInvitationAccepted(false);
//            ChatSpace chatSpace = userInvitation.getChatSpace();
//            chatSpace.removeInvitation(userInvitation);
//            //this.userInvitationRepository.delete(userInvitation);
//            return true;
//        }
        return false;
    }
    //==============================================================================================
}
