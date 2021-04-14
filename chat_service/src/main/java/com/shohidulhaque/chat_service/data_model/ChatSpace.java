package com.shohidulhaque.chat_service.data_model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ChatSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @org.hibernate.annotations.Type(type="uuid-char")
    @Column(unique = true, updatable = false, nullable = false)
    UUID uuid;

    @NotBlank
    @Column(nullable = false)
    String name;


    @Default
    @OneToMany(mappedBy = "chatSpace", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    Set<Member> members = new HashSet<>();


    @Default
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    Set<Message> messages = new HashSet<>();


    @Default
    @OneToMany(mappedBy = "chatSpace", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<UserInvitation> userInvitations = new HashSet<>();

    @Version
    @Column(nullable = false)
    long version;

    public Member addMember(Member member){
        member.setChatSpace(this);
        this.members.add(member);
        return member;
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }

    public UserInvitation addUserInvitation(UserInvitation userInvitation){
        userInvitation.setChatSpace(this);
        this.userInvitations.add(userInvitation);
        return userInvitation;
    }

    public void removeInvitation(UserInvitation userInvitation) {
        userInvitations.remove(userInvitation);
        userInvitation.setChatSpace(null);
    }
}
