package com.shohidulhaque.user_registry_service.repository;

import com.shohidulhaque.user_registry_service.model.PersonRegistration;
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
public class PersonRegistrationRepositoryTest {

    static final UUID UUID_OF_EXISTING_PERSON = UUID.fromString("ee3c41a0-1d7a-4082-816a-5143884bd37d");
    static final UUID UUID_OF_NON_EXISTING_PERSON = UUID.fromString("7ba66e96-392e-425a-8e54-d51246b9a90d");
    static final String EXISTING_EMAIL_ADDRESS = "email@email.com";
    static final String NON_EXISTING_EMAIL_ADDRESS = "i_am_so_tired_of_this_project@email.com";
    static final String EXISTING_NICKNAME = "nickname";
    static final String NON_EXISTING_NICKNAME = "i_am_so_tired_of_this_project";

    @Autowired
    PersonRegistrationRepository testObject;

    @Test
    public void givenAnExistingPerson_whenTheyRetrieved_thenTheyAreReturnedSuccessfully(){
        Optional<PersonRegistration> personRegistrationOptional = this.testObject.findPersonByUserUuid(UUID_OF_EXISTING_PERSON);
        Assertions.assertThat(personRegistrationOptional).hasValueSatisfying( personRegistration ->  {
            Assertions.assertThat(personRegistration.getUuid()).isEqualTo(UUID_OF_EXISTING_PERSON);
        });
    }

    @Test
    public void givenAnNonExistingPerson_whenTheyRetrieved_thenTheyAreReturnedSuccessfully(){
        Optional<PersonRegistration> personRegistrationOptional = this.testObject.findPersonByUserUuid(UUID_OF_NON_EXISTING_PERSON);
        Assertions.assertThat(personRegistrationOptional).isEmpty();
    }

    @Test
    public void givenAnExistingEmailAddress_whenTheEmailIsCheckedToSeeIfItExist_thenACountOfOneIsReturned(){
        long count = this.testObject.emailCount(EXISTING_EMAIL_ADDRESS);
        Assertions.assertThat(count).isEqualTo(1);
    }

    @Test
    public void givenANonExistingEmailAddress_whenTheEmailIsCheckedToSeeIfItExist_thenACountOfZeroIsReturned(){
        long count = this.testObject.emailCount(NON_EXISTING_EMAIL_ADDRESS);
        Assertions.assertThat(count).isEqualTo(0);
    }

    @Test
    public void givenAnExistingNickname_whenTheEmailIsCheckedToSeeIfItExist_thenACountOfOneIsReturned(){
        long count = this.testObject.nicknameCount(EXISTING_NICKNAME);
        Assertions.assertThat(count).isEqualTo(1);
    }

    @Test
    public void givenAnNonExistingNickname_whenTheEmailIsCheckedToSeeIfItExist_thenACountOfOneIsReturned(){
        long count = this.testObject.nicknameCount(NON_EXISTING_NICKNAME);
        Assertions.assertThat(count).isEqualTo(0);
    }

}
