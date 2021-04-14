package com.shohidulhaque.chat_service.config_backup.data_model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInvitation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @org.hibernate.annotations.Type(type="uuid-char")
    UUID uuid;

    @org.hibernate.annotations.Type(type="uuid-char")
    UUID invitedUser;

    Instant creationTimestamp;

    Instant expirationTimestamp;

    @Version
    long version;
}
