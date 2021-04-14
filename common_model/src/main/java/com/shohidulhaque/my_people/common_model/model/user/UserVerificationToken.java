package com.shohidulhaque.my_people.common_model.model.user;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVerificationToken implements Serializable {

  private static final Duration EXPIRATION_LENGTH = Duration.ofHours(24);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String token;

  @Column
  private String userVerificationLinkUri;

  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  @Column
  private Instant expirationTime;

}
