package com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
public class ChatSpace extends ResponsePayload {
//    String name;
//    List<Message> messages;

    @org.hibernate.annotations.Type(type="uuid-char")
    UUID uuid;

    String name;

    Set<Message> messages;
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
//    Set<Member> members;



//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
//    Set<UserInvitation> userInvitations;


}
