package com.shohidulhaque.user_mailbox_service.service;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.ContentUserMailboxSuccessResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.CreateUserMailboxDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.FromUserMailDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMail;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailUpdateReadStatusDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailbox;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailboxDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailboxDtoResponse;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.Undefined;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.success_codes.SuccessCode.SuccessType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import com.shohidulhaque.user_mailbox_service.data_model.InvitationMail;
import com.shohidulhaque.user_mailbox_service.data_model.Mail;
import com.shohidulhaque.user_mailbox_service.data_model.Mailbox;
import com.shohidulhaque.user_mailbox_service.data_transfer_object.validation.MailBoxDtoArgumentViolationResponse;
import com.shohidulhaque.user_mailbox_service.mapper.UserMailboxMapper;
import com.shohidulhaque.user_mailbox_service.repository.UserMailboxRepository;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserMailboxService {

    private final Validator javaxValidator;
    UserMailboxRepository userMailboxRepository;
    UserMailboxMapper userMailboxMapper;
    Clock clock;

    @Autowired
    public UserMailboxService(
        UserMailboxRepository userMailboxRepository,
        UserMailboxMapper userMailboxMapper,
        javax.validation.Validator javaxValidator,
        Clock clock){
        this.userMailboxRepository = userMailboxRepository;
        this.userMailboxMapper = userMailboxMapper;
        this.javaxValidator = javaxValidator;
        this.clock = clock;
    }
    //==============================================================================================
    public UserMailboxDtoResponse getUserMailbox(UUID uuidUser){
            Optional<Mailbox> existingMailboxOptional = this.userMailboxRepository.findMailboxByUuid(uuidUser);
        if(existingMailboxOptional.isEmpty()){
            ResponseType possibleErrorResponseType = new ResponseType(HttpStatus.NOT_FOUND.toString(),
                HttpStatus.NOT_FOUND.value(),
                Undefined.undefined.subCode);
            return MailBoxDtoArgumentViolationResponse.GetResponseBodyForError(possibleErrorResponseType, ErrorType.userMailboxGetUserMailboxGeneral);
        }
        return mailboxFoundSuccessfullyResponse(existingMailboxOptional.get());
    }
    private UserMailboxDtoResponse mailboxFoundSuccessfullyResponse(Mailbox userMailboxDataModel) {
        UserMailbox userMailbox = this.userMailboxMapper.userMailboxEntityToUserMailbox(userMailboxDataModel);
        ContentUserMailboxSuccessResponse<UserMailbox> contentUserMailboxSuccessResponse = new ContentUserMailboxSuccessResponse<>(
            SuccessType.mailboxGetGeneral, userMailbox);
        ResponseType responseType = ResponseType
            .builder()
            .message(HttpStatus.OK.toString())
            .code(HttpStatus.OK.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
        UserMailboxDtoResponse userMailboxDtoResponse = UserMailboxDtoResponse.SuccessUserProfileDtoResponse(responseType, List.of(contentUserMailboxSuccessResponse));
        return userMailboxDtoResponse;
    }
    //==============================================================================================
    public UserMailboxDtoResponse createUserMailbox(CreateUserMailboxDtoRequest userMailboxDtoRequest) {
        Set<ConstraintViolation<UserMailboxDtoRequest>> argumentViolations =  this.javaxValidator.validate(userMailboxDtoRequest);
        if (!argumentViolations.isEmpty()) {
            return MailBoxDtoArgumentViolationResponse.BadArgumentException(
                userMailboxDtoRequest,
                argumentViolations,
                ErrorType.userMailboxCreateUserMailboxGeneral
            );
        }
        Optional<Mailbox> existingMailboxOptional = this.userMailboxRepository.findMailboxByUuid(userMailboxDtoRequest.getUserUuid());
        if(existingMailboxOptional.isPresent()){
            ResponseType possibleErrorResponseType = new ResponseType(HttpStatus.CONFLICT.toString(),
                HttpStatus.CONFLICT.value(),
                Undefined.undefined.subCode);
            return MailBoxDtoArgumentViolationResponse.GetResponseBodyForError(possibleErrorResponseType, ErrorType.userMailboxCreateUserMailboxGeneral);
        }
        Mailbox mailbox = this.userMailboxMapper.createUserMailboxDtoRequestToUserMailboxEntity(userMailboxDtoRequest);
        mailbox.setCreationTime(Instant.now(this.clock));
        Mailbox userMailbox = this.userMailboxRepository.save(mailbox);
        return mailboxHasBeenCreatedSuccessfully(userMailbox);
    }
    private UserMailboxDtoResponse mailboxHasBeenCreatedSuccessfully(Mailbox userMailboxDataModel) {
        UserMailbox userMailbox = this.userMailboxMapper.userMailboxEntityToUserMailbox(userMailboxDataModel);
        ContentUserMailboxSuccessResponse contentUserMailboxSuccessResponse =
            ContentUserMailboxSuccessResponse.builder().type(SuccessType.general).payload(userMailbox).build();
        ResponseType responseType = ResponseType
            .builder()
            .message(HttpStatus.CREATED.toString())
            .code(HttpStatus.CREATED.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
        UserMailboxDtoResponse userProfileDtoResponse = UserMailboxDtoResponse.SuccessUserProfileDtoResponse(responseType, List.of(contentUserMailboxSuccessResponse));
        return userProfileDtoResponse;
    }
    //==============================================================================================
    public UserMailboxDtoResponse saveMail(UUID uuidUser, FromUserMailDtoRequest fromUserMailDtoRequest) {
        Optional<Mailbox> existingMailboxOptional = this.userMailboxRepository.findMailboxByUuid(uuidUser);
        if(existingMailboxOptional.isEmpty()){
            ResponseType possibleErrorResponseType = new ResponseType(HttpStatus.NOT_FOUND.toString(),
                HttpStatus.NOT_FOUND.value(),
                Undefined.undefined.subCode);
            return MailBoxDtoArgumentViolationResponse.GetResponseBodyForError(possibleErrorResponseType, ErrorType.userMailCreateMailGeneral);
        }

        Set<ConstraintViolation<UserMailboxDtoRequest>> argumentViolations =  this.javaxValidator.validate(fromUserMailDtoRequest);
        if (!argumentViolations.isEmpty()) {
            return MailBoxDtoArgumentViolationResponse.BadArgumentException(
                fromUserMailDtoRequest,
                argumentViolations,
                ErrorType.userMailCreateMailGeneral
            );
        }

        Mailbox existingMailbox = existingMailboxOptional.get();
        final InvitationMail mail = this.userMailboxMapper.mailRequestToMailEntity(fromUserMailDtoRequest);
        List<InvitationMail> mailList = existingMailbox.getMail();
        mail.setUuid(UUID.randomUUID());
        mail.setCreationTime(Instant.now());
        mail.setSeen(false);
        mailList.add(mail);
        return mailAddedSuccessfullyResponse(mail);
    }
    private UserMailboxDtoResponse mailAddedSuccessfullyResponse(InvitationMail invitationMail) {
        UserMail userMail = this.userMailboxMapper.entityMailToMail(invitationMail);
        ContentUserMailboxSuccessResponse<UserMail> contentUserMailboxSuccessResponse = new ContentUserMailboxSuccessResponse<>(
            SuccessType.mailboxGeneral, userMail);
        ResponseType responseType = ResponseType
            .builder()
            .message(HttpStatus.CREATED.toString())
            .code(HttpStatus.CREATED.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
        UserMailboxDtoResponse userMailboxDtoResponse = UserMailboxDtoResponse.SuccessUserProfileDtoResponse(responseType, List.of(contentUserMailboxSuccessResponse));
        return userMailboxDtoResponse;
    }
    //==============================================================================================
    public UserMailboxDtoResponse getMail(UUID userUuid, UUID mailUuid) {
        Optional<Mail> mailOptional = this.userMailboxRepository.findMailByMailboxUuidAndMailUuid(userUuid, mailUuid);
        if(mailOptional.isEmpty()){
            ResponseType possibleErrorResponseType = new ResponseType(HttpStatus.NOT_FOUND.toString(),
                HttpStatus.NOT_FOUND.value(),
                Undefined.undefined.subCode);
            return MailBoxDtoArgumentViolationResponse.GetResponseBodyForError(possibleErrorResponseType, ErrorType.userMailUpdateUserMailGeneral);
        }
        return getMailRetrievedSuccessfullyResponse(mailOptional.get());
    }
    private UserMailboxDtoResponse getMailRetrievedSuccessfullyResponse(Mail mail) {
        UserMail userMail = this.userMailboxMapper.entityMailToMail(mail);
        ContentUserMailboxSuccessResponse<UserMail> contentUserMailboxSuccessResponse = new ContentUserMailboxSuccessResponse<>(
            SuccessType.mailboxGeneral, userMail);
        ResponseType responseType = ResponseType
            .builder()
            .message(HttpStatus.OK.toString())
            .code(HttpStatus.OK.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
        UserMailboxDtoResponse userMailboxDtoResponse = UserMailboxDtoResponse.SuccessUserProfileDtoResponse(responseType, List.of(contentUserMailboxSuccessResponse));
        return userMailboxDtoResponse;
    }
    //==============================================================================================
    public UserMailboxDtoResponse mailReadStatus(UUID userUuid, UUID mailUuid, UserMailUpdateReadStatusDtoRequest userMailUpdateReadStatusDtoRequest) {

        Set<ConstraintViolation<UserMailboxDtoRequest>> argumentViolations =  this.javaxValidator.validate(userMailUpdateReadStatusDtoRequest);
        if (!argumentViolations.isEmpty()) {
            return MailBoxDtoArgumentViolationResponse.BadArgumentException(
                userMailUpdateReadStatusDtoRequest,
                argumentViolations,
                ErrorType.userMailUpdateUserMailGeneral
            );
        }

        Optional<Mail> mailOptional = this.userMailboxRepository.findMailByMailboxUuidAndMailUuid(userUuid, mailUuid);
        if(mailOptional.isEmpty()){
            ResponseType possibleErrorResponseType = new ResponseType(HttpStatus.NOT_FOUND.toString(),
                HttpStatus.NOT_FOUND.value(),
                Undefined.undefined.subCode);
            return MailBoxDtoArgumentViolationResponse.GetResponseBodyForError(possibleErrorResponseType, ErrorType.userMailUpdateUserMailGeneral);
        }

        Mail mail = mailOptional.get();
        mail.setSeen(userMailUpdateReadStatusDtoRequest.getRead());
        return mailUpdatedSuccessfullyResponse(mail);
    }
    private UserMailboxDtoResponse mailUpdatedSuccessfullyResponse(Mail mail) {
        UserMail userMail = this.userMailboxMapper.entityMailToMail(mail);
        ContentUserMailboxSuccessResponse<UserMail> contentUserMailboxSuccessResponse = new ContentUserMailboxSuccessResponse<>(
            SuccessType.mailboxGeneral, userMail);
        ResponseType responseType = ResponseType
            .builder()
            .message(HttpStatus.OK.toString())
            .code(HttpStatus.OK.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
        UserMailboxDtoResponse userMailboxDtoResponse = UserMailboxDtoResponse.SuccessUserProfileDtoResponse(responseType, List.of(contentUserMailboxSuccessResponse));
        return userMailboxDtoResponse;
    }
    //==============================================================================================
}
