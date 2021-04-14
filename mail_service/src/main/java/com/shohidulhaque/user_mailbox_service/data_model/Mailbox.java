package com.shohidulhaque.user_mailbox_service.data_model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Data
public class Mailbox {

    public final static String Mail = "Mail";
    public final static String InvitationMail = "InvitationMail";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true, updatable = false, nullable = false)
    @org.hibernate.annotations.Type(type="uuid-char")
    UUID userUuid;

    @Column(nullable = false)
    Instant creationTime;

//    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL)
//    List<Mail> mail = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    List<InvitationMail> mail = new ArrayList<>();

    @Version
    @Column(nullable = false)
    Long version;
}
