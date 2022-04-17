package com.github.metabs.controller.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.metabs.model.User;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class UserJsonSerializer extends JsonSerializer<User> {
  @Override
  public void serialize(User value,
                        JsonGenerator gen,
                        SerializerProvider serializers
  ) throws IOException {

    gen.writeStartObject();

    gen.writeStringField(
        "id",
        value.getId().toString());

    gen.writeStringField(
        "first_name",
        value.getFirstname().getValue());

    gen.writeStringField(
        "last_name",
        value.getLastname().getValue());

    gen.writeStringField(
        "user_name",
        value.getUsername().getValue());

    gen.writeStringField(
        "password",
        value.getPassword().getValue());

    gen.writeEndObject();
  }
}

