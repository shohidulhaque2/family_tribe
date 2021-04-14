package com.shohidulhaque.user_mailbox_service.repository;


import com.shohidulhaque.user_mailbox_service.data_model.Mail;
import com.shohidulhaque.user_mailbox_service.data_model.Mailbox;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserMailboxRepository extends JpaRepository<Mailbox, Long> {
    @Query("select mailbox from Mailbox mailbox where mailbox.userUuid = :userUuid")
    Optional<Mailbox> findMailboxByUuid(UUID userUuid);

    @Query("select mail from Mailbox mailbox inner join mailbox.mail mail where mailbox.userUuid = :userUuid and mail.uuid = :mailUuid")
    Optional<Mail> findMailByMailboxUuidAndMailUuid(UUID userUuid, UUID mailUuid);
}
