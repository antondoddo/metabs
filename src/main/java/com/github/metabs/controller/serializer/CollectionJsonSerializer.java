package com.github.metabs.controller.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.metabs.model.Collection;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class CollectionJsonSerializer extends JsonSerializer<Collection> {

  @Override
  public void serialize(
      Collection value,
      JsonGenerator gen,
      SerializerProvider serializers
  ) throws IOException {

    gen.writeStartObject();

    gen.writeStringField(
        "id",
        value.getId().toString());

    if (value.getParentCollection() != null) {
      gen.writeStringField(
          "parent_id",
          value.getParentCollection().getId().toString());
    } else {
      gen.writeNullField("parent_id");
    }

    gen.writeStringField(
        "name",
        value.getName().getValue());

    if (value.getDescription() != null) {
      gen.writeStringField(
          "description",
          value.getDescription().getValue());
    } else {
      gen.writeNullField("description");
    }

    gen.writeStringField(
        "created",
        value.getCreated().toString());

    if (value.getUpdated() != null) {
      gen.writeStringField(
          "updated",
          value.getUpdated().toString());
    } else {
      gen.writeNullField("updated");
    }

    if (value.getTrashed() != null) {
      gen.writeStringField(
          "trashed",
          value.getTrashed().toString());
    } else {
      gen.writeNullField("trashed");
    }

    gen.writeEndObject();
  }

}
