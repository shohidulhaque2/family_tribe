package com.shohidulhaque.user_registry_service.data_transfer_object;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public class SensitiveStringDeserializer extends StdDeserializer<String> {
  public SensitiveStringDeserializer() {
    this(null);
  }


  public SensitiveStringDeserializer(Class<?> vc) {
    super(vc);
  }

  public String deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    return "";
  }
}

