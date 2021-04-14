package com.shohidulhaque.user_registry_service.service;

import com.shohidulhaque.user_registry_service.repository.BasketRepository;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({})
public class BasketServiceTest {
    //==============================================================================================
    //==============================================================================================
    @Autowired
    Validator validator;
    //==============================================================================================
    @Autowired
    BasketRepository basketRepository;
    //==============================================================================================
    BasketService testObject;

    @BeforeEach
    public void beforeEachTest() {
    }

}
