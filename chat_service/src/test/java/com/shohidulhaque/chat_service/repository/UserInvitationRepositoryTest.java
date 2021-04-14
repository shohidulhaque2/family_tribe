package com.shohidulhaque.chat_service.repository;

import com.shohidulhaque.chat_service.data_model.UserInvitation;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserInvitationRepositoryTest {

    @Autowired
    UserInvitationRepository testObject;

    @Test
    public void getAllInvitations(){
        List<UserInvitation> allInvitations = this.testObject.findAll();
        Assertions.assertThat(allInvitations).hasSize(1);
    }



}
