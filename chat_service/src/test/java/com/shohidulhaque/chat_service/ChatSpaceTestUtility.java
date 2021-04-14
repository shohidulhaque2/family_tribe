package com.shohidulhaque.chat_service;

import com.shohidulhaque.chat_service.data_model.ChatSpace;
import com.shohidulhaque.chat_service.data_model.ChatSpaceUser;
import com.shohidulhaque.chat_service.data_model.Member;
import com.shohidulhaque.chat_service.data_model.Message;
import com.shohidulhaque.chat_service.data_model.UserInvitation;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ChatSpaceTestUtility {


    public static ChatSpaceUser CreateChatSpaceUser(UUID creatorUuid, UUID fromUuid, UUID invitedUuid, Clock clock){
        Set<Member> memberSet = Set.of(Member
            .builder()
            .memberUuid(fromUuid)
            .build());

        Set<UserInvitation> userInvitations = Set.of(UserInvitation
            .builder()
            .invitedUser(invitedUuid)
            .creationTimestamp(Instant.now(clock))
            .expirationTimestamp(Instant.now(clock))
            .build());

        Set<Message> messages = Set.of(Message
            .builder()
            .message("Random Message")
            .createdTimestamp(Instant.now(clock))
            .fromUuid(fromUuid)
//            .uuid(UUID.randomUUID())
            .build());

        List<ChatSpace> chatSpaceList = List.of(ChatSpace
            .builder()
            .name("SOME CHATSPACE NAME")
//            .uuid(UUID.randomUUID())
            .members(memberSet)
            .userInvitations(userInvitations)
            .messages(messages)
            .build());

        return ChatSpaceUser
            .builder()
//            .uuid(UUID.randomUUID())
            .creatorUuid(creatorUuid)
            .chatSpace(chatSpaceList)
            .build();
    }

    public static ChatSpaceUser CreateChatSpaceUser(UUID creatorUuid, UUID fromUuid, Clock clock){
        Set<Member> memberSet = Set.of(Member
            .builder()
            .memberUuid(fromUuid)
            .build());

        Set<UserInvitation> userInvitations = Set.of(UserInvitation
            .builder()
            .invitedUser(UUID.randomUUID())
            .creationTimestamp(Instant.now(clock))
            .expirationTimestamp(Instant.now(clock))
            .build());

        Set<Message> messages = Set.of(Message
            .builder()
            .message("Random Message")
            .createdTimestamp(Instant.now(clock))
            .fromUuid(fromUuid)
//            .uuid(UUID.randomUUID())
            .build());

        List<ChatSpace> chatSpaceList = List.of(ChatSpace
            .builder()
            .name("SOME CHATSPACE NAME")
//            .uuid(UUID.randomUUID())
            .members(memberSet)
            .userInvitations(userInvitations)
            .messages(messages)
            .build());

        return ChatSpaceUser
            .builder()
//            .uuid(UUID.randomUUID())
            .creatorUuid(creatorUuid)
            .chatSpace(chatSpaceList)
            .build();
    }



    public static ChatSpaceUser CreateChatSpaceUser(UUID creatorUuid, Clock clock){
        Set<Member> memberSet = Set.of(Member
            .builder()
            .memberUuid(UUID.randomUUID())
            .build());

        Set<UserInvitation> userInvitations = Set.of(UserInvitation
            .builder()
            .invitedUser(UUID.randomUUID())
            .creationTimestamp(Instant.now(clock))
            .expirationTimestamp(Instant.now(clock))
            .build());

        Set<Message> messages = Set.of(Message
            .builder()
            .message("Random Message")
            .createdTimestamp(Instant.now(clock))
            .fromUuid(UUID.randomUUID())
//            .uuid(UUID.randomUUID())
            .build());

        List<ChatSpace> chatSpaceList = List.of(ChatSpace
            .builder()
            .name("SOME CHATSPACE NAME")
//            .uuid(UUID.randomUUID())
            .members(memberSet)
            .userInvitations(userInvitations)
            .messages(messages)
            .build());

        return ChatSpaceUser
            .builder()
//            .uuid(UUID.randomUUID())
            .creatorUuid(creatorUuid)
            .chatSpace(chatSpaceList)
            .build();
    }

}
