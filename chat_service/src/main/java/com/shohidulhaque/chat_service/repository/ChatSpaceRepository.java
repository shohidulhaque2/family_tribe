package com.shohidulhaque.chat_service.repository;

import com.shohidulhaque.chat_service.data_model.ChatSpace;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatSpaceRepository  extends JpaRepository<ChatSpace, Long> {

    Optional<ChatSpace> findByName(String name);

//    @Query("SELECT c FROM ChatSpace c INNER JOIN c.members m WHERE  c.uuid = :uuidOfChatSpace AND m.memberUuid = :uuidOfMember" )
//    Optional<ChatSpace> findByChatSpaceUuid(UUID chatSpaceCreator, UUID uuidOfChatSpace, UUID uuidOfMember);

    @Query("SELECT c FROM ChatSpaceUser u "
        + "INNER JOIN u.chatSpace c  "
        + "INNER JOIN c.members m "
        + "WHERE u.creatorUuid = :chatSpaceCreator "
        + "AND c.uuid = :uuidOfChatSpace "
        + "AND m.memberUuid = :uuidOfMember" )
    Optional<ChatSpace> findChatSpace(UUID chatSpaceCreator, UUID uuidOfChatSpace, UUID uuidOfMember);

//    @Query("SELECT c FROM ChatSpaceUser u "
//        + "INNER JOIN u.chatSpace c  "
//        + "INNER JOIN c.members m "
//        + "WHERE u.creatorUuid = :chatSpaceCreator "
//        + "AND c.uuid = :uuidOfChatSpace "
//        + "m.uuid")
//    Optional<ChatSpace> findByChatSpace(UUID chatSpaceCreator, UUID uuidOfChatSpace, UUID member);

}
