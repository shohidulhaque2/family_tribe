package com.shohidulhaque.user_registry_service.repository;

import com.shohidulhaque.user_registry_service.model.PersonRegistration;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRegistrationRepository extends JpaRepository<PersonRegistration, Long> {

    @Query("SELECT COUNT(p) FROM com.shohidulhaque.user_registry_service.model.PersonRegistration p WHERE p.email = :email ")
    long emailCount(String email);

    @Query("SELECT COUNT(p) FROM com.shohidulhaque.user_registry_service.model.PersonRegistration p WHERE p.nickname = :nickname ")
    long nicknameCount(String nickname);

//    @Query("select mailbox from Mailbox mailbox where mailbox.userUuid = :userUuid")
//    Optional<Mailbox> findByUserUuid(UUID userUuid);
//
    @Query("select p from com.shohidulhaque.user_registry_service.model.PersonRegistration p where p.uuid = :uuidOfPerson ")
    Optional<PersonRegistration> findPersonByUserUuid(UUID uuidOfPerson);

}
