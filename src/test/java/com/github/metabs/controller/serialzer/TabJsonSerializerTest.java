package com.github.metabs.controller.serialzer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.metabs.MetabsApplication;
import com.github.metabs.controller.serializer.TabJsonSerializer;
import com.github.metabs.model.ObjectMother;
import com.github.metabs.model.Tab;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;


@JsonTest
public class TabJsonSerializerTest {

  @Autowired
  private JacksonTester<Tab> jacksonTester;

  @Before
  public void setup() {
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addSerializer(Tab.class, new TabJsonSerializer());
    objectMapper.registerModule(module);
    JacksonTester.initFields(this, objectMapper);
  }

  @Test
  public void serializeFromJavaToJson() throws Exception {

    Tab tab = ObjectMother.generateRandomTabWithParent();
    JsonContent<Tab> json = jacksonTester.write(tab);

    DocumentContext parsedJson = JsonPath.parse(json.getJson());
    Assert.assertEquals(parsedJson.read("$.id", String.class), tab.getId().toString());
    Assert.assertEquals(parsedJson.read("$.parent_id", String.class), tab.getParentCollection().getId().toString());
    Assert.assertEquals(parsedJson.read("$.name", String.class), tab.getName().getValue());
    Assert.assertEquals(parsedJson.read("$.link", String.class), tab.getLink().toString());
    Assert.assertEquals(parsedJson.read("$.description", String.class), tab.getDescription().getValue());
    Assert.assertEquals(parsedJson.read("$.created", String.class), tab.getCreated().toString());
    Assert.assertNull(parsedJson.read("$.updated", String.class));
    Assert.assertNull(parsedJson.read("$.trashed", String.class));

    tab.rename(ObjectMother.generateRandomName());
    json = jacksonTester.write(tab);
    parsedJson = JsonPath.parse(json.getJson());
    Assert.assertEquals(parsedJson.read("$.updated", String.class), tab.getUpdated().toString());
    Assert.assertNull(parsedJson.read("$.trashed", String.class));

    tab.moveToBin();
    json = jacksonTester.write(tab);
    parsedJson = JsonPath.parse(json.getJson());

    Assert.assertEquals(parsedJson.read("$.updated", String.class), tab.getUpdated().toString());
    Assert.assertEquals(parsedJson.read("$.trashed", String.class), tab.getTrashed().toString());
  }
}
