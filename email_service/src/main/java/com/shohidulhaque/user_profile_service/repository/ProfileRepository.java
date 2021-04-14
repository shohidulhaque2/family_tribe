package com.shohidulhaque.user_profile_service.repository;

import com.shohidulhaque.user_profile_service.data_model.Profile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository <Profile, Long> {
    @Query("select profile from Profile profile where profile.personUuid = :uuidOfPerson")
    Optional<Profile> findProfile(UUID uuidOfPerson);
}
