package com.shohidulhaque.user_registry_service.model;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PersonVerificationToken implements Serializable {

  private static final Duration EXPIRATION_LENGTH = Duration.ofHours(24);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, updatable = false, nullable = false)
  @org.hibernate.annotations.Type(type="uuid-char")
  UUID uuid;

  @Column(nullable = false)
  private String token;

  @Column(nullable = false)
  private String userVerificationLinkUri;

  //remove
//  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
//  @JoinColumn(nullable = false, name = "user_id")
//  private User user;

  @Column(nullable = false)
  private Instant expirationTime;

  @OneToOne(fetch = FetchType.LAZY)
  PersonRegistration personRegistration;

  @Version
  Long version;
}
