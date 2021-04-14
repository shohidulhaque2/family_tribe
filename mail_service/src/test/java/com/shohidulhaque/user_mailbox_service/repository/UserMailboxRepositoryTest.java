package com.shohidulhaque.user_mailbox_service.repository;

import com.shohidulhaque.user_mailbox_service.data_model.InvitationMail;
import com.shohidulhaque.user_mailbox_service.data_model.Mailbox;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMailboxRepositoryTest {

    @Autowired
    UserMailboxRepository testObject;

    @Test
    public void givenANewMailbox_whenItIsSaved_ThenASingleMailboxIsRetrieved(){
        Mailbox mailbox = new Mailbox();
        mailbox.setCreationTime(Instant.now());
        UUID userMailboxUuid = UUID.randomUUID();
        mailbox.setUserUuid(userMailboxUuid);

        InvitationMail mail = new InvitationMail();
        mail.setSeen(false);
        mail.setMessage("Some Message");
        mail.setCreationTime(Instant.now());
        mail.setUuid(UUID.randomUUID());
        mail.setFromUserUuid(UUID.randomUUID());
        mailbox.setMail(List.of(mail));

        this.testObject.save(mailbox);

        Optional<Mailbox> mailboxOptional = this.testObject.findMailboxByUuid(userMailboxUuid);
        Assertions.assertThat(mailboxOptional.isPresent()).isTrue();
        Assertions.assertThat(mailboxOptional.get().getUserUuid()).isEqualTo(userMailboxUuid);
        Assertions.assertThat(this.testObject.findAll()).hasSize(2);
    }

    @Test
    public void givenAMailbox_whenAMailboxIsRetrieved_ThenItIsRetrieved(){
        UUID userMailboxUuid = UUID.fromString("13d993c2-d736-42ce-9926-ec944b02cbcc");
        Optional<Mailbox> mailboxOptional = this.testObject.findMailboxByUuid(userMailboxUuid);

        Assertions.assertThat(mailboxOptional.isPresent()).isTrue();
        Assertions.assertThat(mailboxOptional.get().getUserUuid()).isEqualTo(userMailboxUuid);

    }
}
