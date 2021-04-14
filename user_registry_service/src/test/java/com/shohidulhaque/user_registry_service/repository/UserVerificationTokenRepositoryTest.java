package com.shohidulhaque.user_registry_service.repository;


import com.shohidulhaque.user_registry_service.model.PersonVerificationToken;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserVerificationTokenRepositoryTest {

    final static UUID EXISTING_VERIFICATION_TOKEN = UUID.fromString("2b0a9a91-ea53-44fa-b88e-f2e81d20dccd");
    static final UUID UUID_OF_EXISTING_PERSON = UUID.fromString("ee3c41a0-1d7a-4082-816a-5143884bd37d");

    final static UUID NON_EXISTING_VERIFICATION_TOKEN = UUID.fromString("e2c65269-345d-4ab4-b73f-7d46b12e8024");

    @Autowired
    PersonVerificationTokenRepository personVerificationTokenRepository;

    @Test
    public void givenAnExistingVerificationToken_whenItIsRetrieved_thenItIsReturned(){
        Optional<PersonVerificationToken> personVerificationTokenOptional = this.personVerificationTokenRepository.findVerificationToken(EXISTING_VERIFICATION_TOKEN.toString());
        Assertions.assertThat(personVerificationTokenOptional).hasValueSatisfying( personVerificationToken ->
            Assertions.assertThat(personVerificationToken.getToken()).isEqualTo(EXISTING_VERIFICATION_TOKEN.toString())
        );
    }

    @Test
    public void givenAnANonExistingVerificationToken_whenItIsRetrieved_thenItIsNotFound(){
        Optional<PersonVerificationToken> personVerificationTokenOptional = this.personVerificationTokenRepository.findVerificationToken(NON_EXISTING_VERIFICATION_TOKEN.toString());
        Assertions.assertThat(personVerificationTokenOptional).isEmpty();
    }

    @Test
    public void givenAnExistingVerificationToken_whenItIsRetrieved_thenItIsReturnedAndThePersonTryingToRegister(){
        Optional<PersonVerificationToken> personVerificationTokenOptional = this.personVerificationTokenRepository.findVerificationToken(EXISTING_VERIFICATION_TOKEN.toString());
        Assertions.assertThat(personVerificationTokenOptional).hasValueSatisfying( personVerificationToken -> {
            Assertions.assertThat(personVerificationToken.getToken()).isEqualTo(EXISTING_VERIFICATION_TOKEN.toString());
            Assertions.assertThat(personVerificationToken.getPersonRegistration().getUuid()).isEqualTo(UUID_OF_EXISTING_PERSON);
            }
        );
    }

}
