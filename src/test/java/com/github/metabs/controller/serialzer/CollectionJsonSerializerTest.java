package com.github.metabs.controller.serialzer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.metabs.controller.serializer.CollectionJsonSerializer;
import com.github.metabs.model.Collection;
import com.github.metabs.model.ObjectMother;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
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

    UUID idParent = UUID.randomUUID();
    Name nameParent = ObjectMother.generateRandomName();
    Description descriptionParent = ObjectMother.generateRandomDescription();
    Collection collectionParent = Collection.createCollection(
            idParent, nameParent, descriptionParent
    );

    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    Description description = ObjectMother.generateRandomDescription();

    Collection collection = Collection.createCollectionWithParent(
            id, collectionParent, name, description
    );

    JsonContent<Collection> json = jacksonTester.write(collection);

    assertThat(json)
            .extractingJsonPathStringValue("$.id")
            .isEqualTo(id.toString());
    assertThat(json)
            .extractingJsonPathStringValue("$.parent_id")
            .isEqualTo(collectionParent.getId().toString());
    assertThat(json)
            .extractingJsonPathStringValue("$.name")
            .isEqualTo(name.getValue());
    assertThat(json)
            .extractingJsonPathStringValue("$.description")
            .isEqualTo(description.getValue());
  }
}
