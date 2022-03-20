package com.github.metabs.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.metabs.controller.serializer.CollectionJsonSerializer;
import com.github.metabs.controller.serializer.TabJsonSerializer;
import com.github.metabs.model.Collection;
import com.github.metabs.model.ObjectMother;
import com.github.metabs.model.Tab;
import com.github.metabs.model.dto.SaveElementDto;
import com.github.metabs.model.dto.validator.RequestElementValidator;
import com.github.metabs.service.ElementService;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;


@RunWith(MockitoJUnitRunner.class)
public class ElementControllerTest {

  private MockMvc mockMvc;

  @Mock
  private ElementService elementService;

  @Mock
  private RequestElementValidator validator;

  @Mock
  private Errors errors;

  @InjectMocks
  private ElementController elementController;

  @Before
  public void setup() {
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addSerializer(Tab.class, new TabJsonSerializer());
    module.addSerializer(Collection.class, new CollectionJsonSerializer());
    objectMapper.registerModule(module);
    JacksonTester.initFields(this, objectMapper);

    MappingJackson2HttpMessageConverter converter =
        new MappingJackson2HttpMessageConverter();

    converter.setObjectMapper(objectMapper);

    mockMvc = MockMvcBuilders
        .standaloneSetup(elementController)
        .setMessageConverters(converter)
        .build();
  }

  @Test
  public void shouldReturnElementById() throws Exception {

    Tab tab = ObjectMother.generateRandomTab();

    Mockito.when(
        elementService.getElementById(tab.getId())
    ).thenReturn(Optional.of(tab));

    RequestBuilder request = MockMvcRequestBuilders
        .get("/elements/" + tab.getId().toString())
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(request).andReturn();

    MockHttpServletResponse response = result.getResponse();
    Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    String content = response.getContentAsString();
    DocumentContext outputJson = JsonPath.parse(content);
    Assert.assertEquals(outputJson.read("$.id", String.class), tab.getId().toString());
    Assert.assertNull(outputJson.read("$.parent_id", String.class));
    Assert.assertEquals(outputJson.read("$.name", String.class), tab.getName().getValue());
    Assert.assertEquals(outputJson.read("$.link", String.class), tab.getLink().toString());
    Assert.assertEquals(outputJson.read(
        "$.description", String.class
    ), tab.getDescription().getValue());
    Assert.assertEquals(outputJson.read("$.created", String.class), tab.getCreated().toString());
    Assert.assertNull(outputJson.read("$.updated", String.class));
    Assert.assertNull(outputJson.read("$.trashed", String.class));
  }

  @Test
  public void shouldReturnNotFound() throws Exception {

    Tab tab = ObjectMother.generateRandomTab();

    Mockito.when(
        elementService.getElementById(tab.getId())
    ).thenReturn(Optional.empty());

    RequestBuilder request = MockMvcRequestBuilders
        .get("/elements/" + tab.getId().toString())
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(request).andReturn();

    MockHttpServletResponse response = result.getResponse();
    Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
  }

  @Test
  public void shouldSaveTab() throws Exception {

    Tab tab = ObjectMother.generateRandomTab();
    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.rename(tab.getName());
    saveElementDto.changeDescription(tab.getDescription());
    saveElementDto.changeLink(tab.getLink());

    Map<String, String> input = new HashMap<>();
    input.put("name", tab.getName().getValue());
    input.put("description", tab.getDescription().getValue());
    input.put("link", tab.getLink().toString());

    Mockito.when(validator.getSaveElementDto()).thenReturn(saveElementDto);
    Mockito.when(elementService.saveElement(saveElementDto)).thenReturn(tab);

    ObjectMapper objectMapper = new ObjectMapper();
    String inputJson = objectMapper.writeValueAsString(input);

    RequestBuilder request = MockMvcRequestBuilders
        .post("/elements/")
        .content(inputJson)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc
        .perform(request)
        .andExpect(jsonPath("$.id").value(tab.getId().toString()))
        .andExpect(jsonPath("$.parent_id").doesNotExist())
        .andExpect(jsonPath("$.name").value(tab.getName().getValue()))
        .andExpect(jsonPath("$.link").value(tab.getLink().toString()))
        .andExpect(jsonPath("$.description").value(tab.getDescription().getValue()))
        .andExpect(jsonPath("$.created").value(tab.getCreated().toString()))
        .andExpect(jsonPath("$.updated").doesNotExist())
        .andExpect(jsonPath("$.trashed").doesNotExist())
        .andExpect(status().isCreated());
  }

  @Test
  public void shouldSaveTabWithParent() throws Exception {

    Tab tab = ObjectMother.generateRandomTabWithParent();
    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.rename(tab.getName());
    saveElementDto.changeDescription(tab.getDescription());
    saveElementDto.changeLink(tab.getLink());


    Map<String, String> input = new HashMap<>();
    input.put("name", tab.getName().getValue());
    input.put("description", tab.getDescription().getValue());
    input.put("link", tab.getLink().toString());

    Mockito.when(validator.getSaveElementDto()).thenReturn(saveElementDto);
    Mockito.when(elementService.saveElementWithParent(
        saveElementDto,
        tab.getParentCollection().getId()
    )).thenReturn(tab);

    ObjectMapper objectMapper = new ObjectMapper();
    String inputJson = objectMapper.writeValueAsString(input);

    RequestBuilder request = MockMvcRequestBuilders
        .post("/elements/" + tab.getParentCollection().getId())
        .content(inputJson)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc
        .perform(request)
        .andExpect(jsonPath("$.id").value(tab.getId().toString()))
        .andExpect(jsonPath("$.parent_id").value(
            tab.getParentCollection().getId().toString()
            )
        )
        .andExpect(jsonPath("$.name").value(tab.getName().getValue()))
        .andExpect(jsonPath("$.link").value(tab.getLink().toString()))
        .andExpect(jsonPath("$.description").value(tab.getDescription().getValue()))
        .andExpect(jsonPath("$.created").value(tab.getCreated().toString()))
        .andExpect(jsonPath("$.updated").doesNotExist())
        .andExpect(jsonPath("$.trashed").doesNotExist())
        .andExpect(status().isCreated());
  }

  @Test
  public void shouldSaveCollection() throws Exception {

    Collection collection = ObjectMother.generateRandomCollection();
    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.rename(collection.getName());
    saveElementDto.changeDescription(collection.getDescription());

    Map<String, String> input = new HashMap<>();
    input.put("name", collection.getName().getValue());
    input.put("description", collection.getDescription().getValue());

    Mockito.when(validator.getSaveElementDto()).thenReturn(saveElementDto);
    Mockito.when(elementService.saveElement(saveElementDto)).thenReturn(collection);

    ObjectMapper objectMapper = new ObjectMapper();
    String inputJson = objectMapper.writeValueAsString(input);

    RequestBuilder request = MockMvcRequestBuilders
        .post("/elements/")
        .content(inputJson)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc
        .perform(request)
        .andExpect(jsonPath("$.id").value(collection.getId().toString()))
        .andExpect(jsonPath("$.name").value(collection.getName().getValue()))
        .andExpect(jsonPath("$.link").doesNotExist())
        .andExpect(jsonPath("$.description").value(
            collection.getDescription().getValue())
        )
        .andExpect(jsonPath("$.created").value(collection.getCreated().toString()))
        .andExpect(jsonPath("$.updated").doesNotExist())
        .andExpect(jsonPath("$.trashed").doesNotExist())
        .andExpect(status().isCreated());
  }

  @Test
  public void shouldSaveCollectionWithParent() throws Exception {

    Collection collection = ObjectMother.generateRandomCollectionWithParent();
    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.rename(collection.getName());
    saveElementDto.changeDescription(collection.getDescription());

    Map<String, String> input = new HashMap<>();
    input.put("name", collection.getName().getValue());
    input.put("description", collection.getDescription().getValue());

    Mockito.when(validator.getSaveElementDto()).thenReturn(saveElementDto);
    Mockito.when(elementService.saveElementWithParent(
        saveElementDto,
        collection.getParentCollection().getId()
    )).thenReturn(collection);

    ObjectMapper objectMapper = new ObjectMapper();
    String inputJson = objectMapper.writeValueAsString(input);

    RequestBuilder request = MockMvcRequestBuilders
        .post("/elements/" + collection.getParentCollection().getId())
        .content(inputJson)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc
        .perform(request)
        .andExpect(jsonPath("$.id").value(collection.getId().toString()))
        .andExpect(jsonPath("$.parent_id").value(
            collection.getParentCollection().getId().toString()
            )
        )
        .andExpect(jsonPath("$.name").value(collection.getName().getValue()))
        .andExpect(jsonPath("$.description").value(collection.getDescription().getValue()))
        .andExpect(jsonPath("$.created").value(collection.getCreated().toString()))
        .andExpect(jsonPath("$.updated").doesNotExist())
        .andExpect(jsonPath("$.trashed").doesNotExist())
        .andExpect(status().isCreated());
  }
}
