package com.github.metabs.controller.serialzer;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.metabs.model.Collection;
import com.github.metabs.model.ObjectMother;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.util.UUID;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;


@JsonTest
public class CollectionJsonSerializerTest {

  @Autowired
  private JacksonTester<Collection> jacksonTester;

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

    assertThat(json).extractingJsonPathStringValue("$.id")
        .isEqualTo(id.toString());
    assertThat(json).extractingJsonPathStringValue("$.parent_id")
        .isEqualTo(collectionParent);
    assertThat(json).extractingJsonPathStringValue("$.name")
        .isEqualTo(name.getValue());
    assertThat(json).extractingJsonPathStringValue("$.description")
        .isEqualTo(description.getValue());
  }
}
