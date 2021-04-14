package com.shohidulhaque.chat_service.repository;

import com.shohidulhaque.chat_service.data_model.Message;
import com.shohidulhaque.chat_service.data_model.UserInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
