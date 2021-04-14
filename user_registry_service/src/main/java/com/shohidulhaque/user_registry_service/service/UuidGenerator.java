package com.shohidulhaque.user_registry_service.service;

import java.util.UUID;
import org.springframework.util.Assert;

//@Component
public class UuidGenerator {
  public UUID getUuid() {
    return UUID.randomUUID();
  }

  public UUID getUuid(String str) {
    Assert.notNull(str, "str cannot be null.");
    return UUID.nameUUIDFromBytes(str.getBytes());
  }
}
