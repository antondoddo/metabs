package com.github.metabs.controller.serialzer;


import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.metabs.model.Collection;
import com.github.metabs.model.ObjectMother;
import com.github.metabs.model.Tab;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.net.URL;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;


@JsonTest
//@RunWith(SpringRunner.class)
public class TabJsonSerializerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private JacksonTester<Tab> jacksonTester;

  //@BeforeEach
  //public void setup() {
  //JacksonTester.initFields(this, new ObjectMapper());
  //}

  @Test
  public void serializeFromJavaToJson() throws Exception {

    Assert.assertNotNull(objectMapper);
    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    URL link = ObjectMother.generateRandomLink();
    Description description = ObjectMother.generateRandomDescription();


    UUID idParent = UUID.randomUUID();
    Name nameParent = ObjectMother.generateRandomName();
    Description descriptionParent = ObjectMother.generateRandomDescription();
    Collection collectionParent = Collection.createCollection(id, name, description);
    Tab tab = Tab.createTabWithParent(id, collectionParent, name, link, description);


    JsonContent<Tab> json = jacksonTester.write(tab);

    assertThat(json).extractingJsonPathStringValue("$.id").isEqualTo(id.toString());
    assertThat(json).extractingJsonPathStringValue("$.parent_id").isEqualTo(collectionParent);
    assertThat(json).extractingJsonPathStringValue("$.name").isEqualTo(name.getValue());
    assertThat(json).extractingJsonPathStringValue("$.link").isEqualTo(link.toString());
    assertThat(json).extractingJsonPathStringValue("$.description").isEqualTo(description.getValue());
    assertThat(json).extractingJsonPathStringValue("$.created").isEqualTo(tab.getCreated().toString());
  }
}
