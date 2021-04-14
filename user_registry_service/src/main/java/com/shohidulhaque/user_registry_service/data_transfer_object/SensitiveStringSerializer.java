package com.shohidulhaque.user_registry_service.data_transfer_object;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class SensitiveStringSerializer extends StdSerializer<String> {

  public SensitiveStringSerializer(){
    super(String.class);
  }

  public SensitiveStringSerializer(Class<String> t) {
    super(t);
  }

  public SensitiveStringSerializer(JavaType type) {
    super(type);
  }

  public SensitiveStringSerializer(Class<?> t, boolean dummy) {
    super(t, dummy);
  }

  public SensitiveStringSerializer(StdSerializer<?> src) {
    super(src);
  }

  @Override
  public void serialize(String value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeObject(value);
  }
}
