package com.shohidulhaque.chat_service.config_backup.data_model;

import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

//    @org.hibernate.annotations.Type(type="uuid-char")
    UUID uuid;

    String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    Set<Member> members;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    Set<Message> messages;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    Set<UserInvitation> userInvitations;

    @Version
    long version;
}
