package com.shohidulhaque.user_registry_service.repository;

import com.shohidulhaque.user_registry_service.model.PersonVerificationToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonVerificationTokenRepository extends
    JpaRepository<PersonVerificationToken, Long> {

  @Query("select u from PersonVerificationToken u where u.token = :userVerificationToken")
  Optional<PersonVerificationToken> findVerificationToken(String userVerificationToken);

}
