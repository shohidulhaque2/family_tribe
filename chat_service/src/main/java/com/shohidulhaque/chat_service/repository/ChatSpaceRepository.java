package com.shohidulhaque.chat_service.repository;

import com.shohidulhaque.chat_service.data_model.ChatSpace;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSpaceRepository  extends JpaRepository<ChatSpace, Long> {

    Optional<ChatSpace> findByName(String name);
}
