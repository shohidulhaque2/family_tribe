package com.shohidulhaque.user_registry_service.model;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Builder
public class PersonRegistration implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(unique = true, updatable = false, nullable = false)
  @org.hibernate.annotations.Type(type="uuid-char")
  UUID uuid;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

//  //remove
//  @NotBlank
//  private String password;
//
//  //remove
//  @NotBlank
//  private String matchingPassword;

  //  @ValidEmail//remove
  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @NotBlank
  @Column(nullable = false, unique = true)
  private String nickname;

  @Column(nullable = false)
  private String identityServerUserId;

  @OneToOne(mappedBy = "personRegistration", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  PersonVerificationToken personVerificationToken;

  public void addPersonVerificationToken(PersonVerificationToken personVerificationToken){
    this.personVerificationToken = personVerificationToken;
    personVerificationToken.setPersonRegistration(this);
  }

  public void removePersonVerificationToken(PersonVerificationToken personVerificationToken){
    this.personVerificationToken = null;
    personVerificationToken.setPersonRegistration(null);
  }

  @Version
  Long version;
}
