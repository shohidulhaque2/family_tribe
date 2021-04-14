package com.shohidulhaque.chat_service.data_model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
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
@EqualsAndHashCode
@ToString
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Version
    Long version;

    @org.hibernate.annotations.Type(type="uuid-char")
    @NotNull
    @Column(unique = true, updatable = false, nullable = false)
    UUID uuid;

    @NotBlank
    @Column(nullable = false)
    String message;

    @NotNull
    @Column(nullable = false)
    Instant createdTimestamp;

    @NotNull
    @org.hibernate.annotations.Type(type="uuid-char")
    @Column(nullable = false)
    UUID fromUuid;
}
