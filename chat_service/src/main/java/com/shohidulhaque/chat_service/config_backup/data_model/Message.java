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
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Version
    long version;

//    @org.hibernate.annotations.Type(type="uuid-char")
    UUID uuid;

    String message;

    Instant createdTimestamp;

    @org.hibernate.annotations.Type(type="uuid-char")
    UUID fromUuid;
}
