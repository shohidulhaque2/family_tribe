package com.shohidulhaque.chat_service.data_transfer_object.validation.message_keys;

import org.springframework.core.io.ClassPathResource;

public interface ValidationMessageKeys {

  ClassPathResource MessageKeyValueFile = new ClassPathResource("messages.properties");

  String UserDoesNotExistMessageKey = "{com.shohidulhaque.chat_service.data_transfer_object.validation.UserDoesNotExist.message}";
  String ChatSpaceAlreadyExistMessageKey = "{com.shohidulhaque.chat_service.data_transfer_object.validation.ChatSpaceAlreadyExist.message}";
  String ChatSpaceNameToWrongLengthMessageKey = "{com.shohidulhaque.chat_service.data_transfer_object.validation.WrongLength.message}";
  String InvalidUserUuid = "{com.shohidulhaque.chat_service.data_transfer_object.validation.UserIdCannotBeInvalid.message}";
}
