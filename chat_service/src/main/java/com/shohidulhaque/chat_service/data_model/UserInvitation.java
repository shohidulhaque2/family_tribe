package com.shohidulhaque.chat_service.data_model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"chatSpace"})
@ToString(exclude = {"chatSpace"})
public class UserInvitation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

//    @GenericGenerator(
//        name = "UUID",
//        strategy = "org.hibernate.id.UUIDGenerator")
    @org.hibernate.annotations.Type(type="uuid-char")
    @Column(unique = true, updatable = false, nullable = false)
    UUID uuid;

    @org.hibernate.annotations.Type(type="uuid-char")
    @NotNull
    @Column(nullable = false)
    UUID invitedUser;

    @Column(nullable = false)
    boolean isInvitationAccepted;

    @NotNull
    @Column(nullable = false)
    Instant creationTimestamp;

    @NotNull
    @Column(nullable = false)
    Instant expirationTimestamp;

    @Version
    @Column(nullable = false)
    Long version;


    @ManyToOne(fetch = FetchType.LAZY)
    ChatSpace chatSpace;
}
