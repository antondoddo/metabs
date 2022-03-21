package com.github.metabs.controller.serialzer;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.metabs.controller.serializer.CollectionJsonSerializer;
import com.github.metabs.model.Collection;
import com.github.metabs.model.ObjectMother;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;


@JsonTest
public class CollectionJsonSerializerTest {

  @Autowired
  private JacksonTester<Collection> jacksonTester;

  @Before
  public void setup() {
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addSerializer(Collection.class, new CollectionJsonSerializer());
    objectMapper.registerModule(module);
    JacksonTester.initFields(this, objectMapper);
  }

  @Test
  public void serializeFromJavaToJson() throws Exception {

    Collection collection = ObjectMother.generateRandomCollectionWithParent();
    JsonContent<Collection> json = jacksonTester.write(collection);
    assertThat(json)
        .extractingJsonPathStringValue("$.id")
        .isEqualTo(collection.getId().toString());
    assertThat(json)
        .extractingJsonPathStringValue("$.parent_id")
        .isEqualTo(collection.getParentCollection().getId().toString());
    assertThat(json)
        .extractingJsonPathStringValue("$.name")
        .isEqualTo(collection.getName().getValue());
    assertThat(json)
        .extractingJsonPathStringValue("$.description")
        .isEqualTo(collection.getDescription().getValue());
  }

}
