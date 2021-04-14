package com.shohidulhaque.user_registry_service.repository;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BasketRepositoryTest {

    static final UUID UUID_OF_EXISTING_PERSON = UUID
        .fromString("ee3c41a0-1d7a-4082-816a-5143884bd37d");
    static final UUID UUID_OF_NON_EXISTING_PERSON = UUID
        .fromString("7ba66e96-392e-425a-8e54-d51246b9a90d");
    static final String EXISTING_EMAIL_ADDRESS = "email@email.com";
    static final String NON_EXISTING_EMAIL_ADDRESS = "i_am_so_tired_of_this_project@email.com";
    static final String EXISTING_NICKNAME = "nickname";
    static final String NON_EXISTING_NICKNAME = "i_am_so_tired_of_this_project";

    @Autowired
    BasketRepository testObject;

}
