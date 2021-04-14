package com.shohidulhaque.user_mailbox_service.mapper;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.CreateUserMailboxDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.FromUserMailDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMail;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailbox;
import com.shohidulhaque.user_mailbox_service.data_model.InvitationMail;
import com.shohidulhaque.user_mailbox_service.data_model.Mail;
import com.shohidulhaque.user_mailbox_service.data_model.Mailbox;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserMailboxMapperTest {

    UserMailboxMapper testObject;

    @BeforeEach
    public void beforeEachTest(){
        this.testObject = UserMailboxMapper.INSTANCE;
    }

    @Test
    public void givenAMailEntity_WhenItIsMapped_ThenAUserMailIsReturned(){

        Mail mail = new Mail();
        UUID mailUuid = UUID.randomUUID();
        mail.setUuid(mailUuid);
        mail.setMessage("Some Message");
        Instant creationTime = Instant.now();
        mail.setCreationTime(creationTime);
        mail.setSeen(true);
        UUID fromUserUuid = UUID.randomUUID();
        mail.setFromUserUuid(fromUserUuid);

        UserMail result = this.testObject.entityMailToMail(mail);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getSeen()).isTrue();
        Assertions.assertThat(result.getMessage()).isEqualTo("Some Message");
        Assertions.assertThat(result.getCreationTime()).isEqualTo(creationTime);
        Assertions.assertThat(result.getUuid()).isEqualTo(mailUuid);
        Assertions.assertThat(result.getFromUserUuid()).isEqualTo(fromUserUuid);
    }

    @Test
    public void givenMailRequest_WhenItIsMapped_ThenAMailEntityIsReturned3(){
        FromUserMailDtoRequest fromUserMailDtoRequest = new FromUserMailDtoRequest();
        UUID fromUserUuid = UUID.randomUUID();
        fromUserMailDtoRequest.setFromUserUuid(fromUserUuid);
        fromUserMailDtoRequest.setMessage("Some Message");

        Mail result = this.testObject.mailRequestToMailEntity(fromUserMailDtoRequest);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getFromUserUuid()).isEqualTo(fromUserUuid);
        Assertions.assertThat(result.getMessage()).isEqualTo("Some Message");
    }

    @Test
    public void givenAMailboxDtoRequest_WhenItIsMapped_ThenAUserMailboxEntityIsReturned(){
        UUID userUuid = UUID.randomUUID();
        CreateUserMailboxDtoRequest createUserMailboxDtoRequest = new CreateUserMailboxDtoRequest();
        createUserMailboxDtoRequest.setUserUuid(userUuid);

        Mailbox result = this.testObject.createUserMailboxDtoRequestToUserMailboxEntity(createUserMailboxDtoRequest);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getUserUuid()).isEqualTo(userUuid);
    }

    @Test
    public void givenAMailboxEntity_WhenItIsMapped_ThenAMailboxResponseIsReturned(){
        Mailbox mailbox = new Mailbox();
        UUID userUuid = UUID.randomUUID();
        mailbox.setUserUuid(userUuid);
        Instant mailboxCreationTime = Instant.now();
        mailbox.setCreationTime(mailboxCreationTime);

        InvitationMail mail = new InvitationMail();
        UUID uuid = UUID.randomUUID();
        Instant mailCreationTime = Instant.now();
        String someMessage = "SomeMessage";
        mail.setUuid(uuid);
        mail.setCreationTime(mailCreationTime);
        mail.setMessage(someMessage);
        mail.setSeen(true);

        mailbox.setMail(List.of(mail));

        UserMailbox result = this.testObject.userMailboxEntityToUserMailbox(mailbox);

        Assertions.assertThat(result.getCreationTime()).isEqualTo(mailboxCreationTime);
        Assertions.assertThat(result.getUserUuid()).isEqualTo(userUuid);

        UserMail[] resultUserMail = result.getMail();
        Assertions.assertThat(resultUserMail).isNotEmpty();
        Assertions.assertThat(resultUserMail).hasSize(1);
        Assertions.assertThat(resultUserMail[0].getCreationTime()).isEqualTo(mailCreationTime);
        Assertions.assertThat(resultUserMail[0].getMessage()).isEqualTo(someMessage);
        Assertions.assertThat(resultUserMail[0].getUuid()).isEqualTo(uuid);
        Assertions.assertThat(resultUserMail[0].getSeen()).isEqualTo(true);
    }


}
