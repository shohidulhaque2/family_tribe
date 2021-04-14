package com.shohidulhaque.user_profile_service.data_model;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @org.hibernate.annotations.Type(type="uuid-char")
    @Column(unique = true, updatable = false, nullable = false)
    UUID uuid;

    @Column(nullable = false)
    UUID personUuid;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String birthDate;

    @Column(nullable = false)
    Instant creationTime;

    @Column(nullable = false)
    Instant updateTime;
}
