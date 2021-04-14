package com.shohidulhaque.chat_service.repository;

import com.shohidulhaque.chat_service.data_model.ChatSpace;
import com.shohidulhaque.chat_service.data_model.ChatSpaceUser;
import com.shohidulhaque.chat_service.data_model.UserInvitation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatSpaceUserRepository extends JpaRepository<ChatSpaceUser, Long> {

    @Query("SELECT c FROM ChatSpaceUser c WHERE c.creatorUuid = :uuid")
    Optional<ChatSpaceUser> getChatSpaceUser(UUID uuid);

    @Query("SELECT chatSpace FROM ChatSpaceUser c INNER JOIN c.chatSpace chatSpace WHERE c.creatorUuid = :uuid")
    List<ChatSpace> getUsersChatSpaces(UUID uuid);

    @Query("SELECT chatSpace FROM ChatSpaceUser c INNER JOIN c.chatSpace chatSpace WHERE c.creatorUuid = :uuid AND chatSpace.name = :chatSpaceName")
    Optional<ChatSpace> getChatSpace(UUID uuid, String chatSpaceName);

    @Query("SELECT chatSpace FROM ChatSpaceUser c INNER JOIN c.chatSpace chatSpace WHERE c.creatorUuid = :uuid AND chatSpace.uuid = :chatSpaceUuid")
    Optional<ChatSpace> getChatSpace(UUID uuid, UUID chatSpaceUuid);

//    @Query("SELECT userInvitation FROM ChatSpaceUser  c "
//        + "INNER JOIN c.chatSpace chatSpace "
//        + "INNER JOIN chatSpace.userInvitations userInvitation  "
//        + "WHERE c.uuid = :chatSpaceUserUuid AND userInvitation.uuid = :invitationUuid AND userInvitation.invitedUser = :invitedUserUuid")
//    List<UserInvitation> getInvitation(UUID chatSpaceUserUuid, UUID invitedUserUuid,UUID invitationUuid);

    @Query("SELECT userInvitation FROM ChatSpaceUser chatSpaceUser "
            + "INNER JOIN chatSpaceUser.chatSpace chatSpace "
            + "INNER JOIN chatSpace.userInvitations userInvitation "
            + "WHERE chatSpaceUser.creatorUuid = :chatSpaceUserUuid "
            + "AND userInvitation.invitedUser = :invitedUserUuid "
            + "AND userInvitation.uuid = :invitationUuid")
    Optional<UserInvitation> getInvitation(UUID chatSpaceUserUuid, UUID invitedUserUuid, UUID invitationUuid);


    @Query("SELECT userInvitation FROM ChatSpaceUser chatSpaceUser "
         + "INNER JOIN chatSpaceUser.chatSpace chatSpace "
         + "INNER JOIN chatSpace.userInvitations userInvitation "
         + "WHERE chatSpaceUser.creatorUuid = :chatSpaceUserUuid "
         + "AND userInvitation.uuid = :uuidOfExistingInvitationForInvitedPerson "
         + "AND userInvitation.invitedUser = :uuidOfInvitedPerson")
    List<UserInvitation> getInvitation2(UUID chatSpaceUserUuid, UUID uuidOfInvitedPerson, UUID uuidOfExistingInvitationForInvitedPerson);

}
