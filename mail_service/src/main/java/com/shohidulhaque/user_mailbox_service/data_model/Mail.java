package com.shohidulhaque.user_mailbox_service.data_model;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

//@AllArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode
//@Entity
//@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "mail_type")
public class Mail {
    //https://jaketrent.com/post/hibernate-boolean-conversion
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Type(type="uuid-char")
    @Column(unique = true, updatable = false, nullable = false)
    UUID uuid;

    @Type(type="uuid-char")
    @Column(nullable = false)
    UUID fromUserUuid;

    @Column(nullable = false)
    Instant creationTime;

    @Column(nullable = false)
    String message;

//    @org.hibernate.annotations.Type(type="uuid-char")
//    UUID invitationUuid;

    //@Type(type="boolean")
    //cannot name variable read. hibernate cannot generate grammar
    @Column(nullable = false)
    boolean seen;

    @Version
    @Column(nullable = false)
    Long version;

}
