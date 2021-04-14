package com.shohidulhaque.my_people.common_model.model.user;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO: decide what to do with these details. all of it should be stored on the identity server
@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

  @Version
  Long version;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //TODO: add this to the database. create random.uuuid() + '-' + uuid(email), or maybe just random.uuuid()
  //this needs to be unqiue
  @Column
  private String uuid;

  @Column
  private String identityServerUserId;

  @Column
  private String firstName;

  @Column
  private String lastName;

  //this needs to be unqiue
  @Column
  private String username;

  //decide if i want to keep the user passwords. removes dependence from any authorisation server during migration
  //but destroys the purpose of an external authorisation mechanism such as OAuth2. I will probably remove this.
  @Deprecated
  @Column
  private String password;

  //this needs to be unqiue
  @Column
  private String email;

  //any new account is disabled by default.
  @Column
  private boolean enabled = false;

  //by default the account has not expired.
  @Column
  private boolean accountNonExpired = true;

  //TODO: add this eventually
  //by default the account is valid until the given date. this is for paid customers.
  //null means active and there is no expiration date.
  //@Column
  //private LocalDate accountValidUntilDate;

  //by default the account credentials has not expired.
  @Column
  private boolean credentialsNonExpired = true;

  //ADD AccountStatus object
  //CREATE CUSTOM USER DETAILS and USER DETAILS SERVICE
//https://docs.spring.io/spring-security/site/docs/4.2.15.RELEASE/apidocs/org/springframework/security/core/userdetails/UserDetails.html
//  enum ACCOUNT_LOCKED_REASON { ACCOUNT_NOT_ACTIVATED}
//
//  @Enumerated(EnumType.STRING)
//  @Column
//  private AccountStatus.ACCOUNT_LOCKED_REASON reasonAccountIsLocked = AccountStatus.ACCOUNT_LOCKED_REASON.ACCOUNT_NOT_ACTIVATED;
//

  //TODO: add this eventually
  //the credential is valid until the given date
  //null means active and there is no expiration date.
  //@Column
  //LocalDate credentialsValidUntilDate;
  @Column
  private boolean accountNonLocked = true;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "role_user", joinColumns = {
      @JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {
          @JoinColumn(name = "role_id", referencedColumnName = "id")})
  private List<Role> roles = new LinkedList<>();

}
