package com.shohidulhaque.user_profile_service.service;

import com.shohidulhaque.user_profile_service.config.message.MessageSourceConfiguration;
import com.shohidulhaque.user_profile_service.config.validation.ValidatorConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
    ValidatorConfiguration.class,
    MessageSourceConfiguration.class,
})
public class UserProfileServiceTest {

    @BeforeEach
    public void beforeEachTest(){

    }

    @Test
    public void dummyTest(){

    }
}
