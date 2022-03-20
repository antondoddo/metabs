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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(MockitoJUnitRunner.class)
public class ElementControllerTest {

  private MockMvc mockMvc;

  @Mock
  private ElementService elementService;

  @Spy
  private RequestElementValidator validator = new RequestElementValidator(new SaveElementDto());

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

    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

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

    mockMvc.perform(request)
            .andExpect(jsonPath("$.id").value(tab.getId().toString()))
            .andExpect(jsonPath("$.parent_id").doesNotExist())
            .andExpect(jsonPath("$.name").value(tab.getName().getValue()))
            .andExpect(jsonPath("$.link").value(tab.getLink().toString()))
            .andExpect(jsonPath("$.description").value(tab.getDescription().getValue()))
            .andExpect(jsonPath("$.created").value(tab.getCreated().toString()))
            .andExpect(jsonPath("$.updated").doesNotExist())
            .andExpect(jsonPath("$.trashed").doesNotExist())
            .andExpect(status().isOk());
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

    mockMvc.perform(request).andExpect(status().isNotFound());
  }

  @Test
  public void shouldSaveTab() throws Exception {
    Tab tab = ObjectMother.generateRandomTab();

    Mockito.when(elementService.saveElement(Mockito.argThat((SaveElementDto dto) -> {
      Assert.assertEquals(dto.getName(), tab.getName());
      Assert.assertEquals(dto.getDescription(), tab.getDescription());
      Assert.assertEquals(dto.getLink(), tab.getLink());
      return true;
    }))).thenReturn(tab);

    Map<String, String> input = new HashMap<>();
    input.put("name", tab.getName().getValue());
    input.put("description", tab.getDescription().getValue());
    input.put("link", tab.getLink().toString());

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

    Mockito.when(elementService.saveElementWithParent(
            Mockito.argThat((SaveElementDto dto) -> {
              Assert.assertEquals(dto.getName(), tab.getName());
              Assert.assertEquals(dto.getDescription(), tab.getDescription());
              Assert.assertEquals(dto.getLink(), tab.getLink());
              return true;
            }),
            Mockito.argThat((UUID id) -> id.equals(tab.getParentCollection().getId()))
    )).thenReturn(tab);

    Map<String, String> input = new HashMap<>();
    input.put("name", tab.getName().getValue());
    input.put("description", tab.getDescription().getValue());
    input.put("link", tab.getLink().toString());

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

    Mockito.when(elementService.saveElement(Mockito.argThat((SaveElementDto dto) -> {
      Assert.assertEquals(dto.getName(), collection.getName());
      Assert.assertEquals(dto.getDescription(), collection.getDescription());
      Assert.assertNull(dto.getLink());
      return true;
    }))).thenReturn(collection);

    Map<String, String> input = new HashMap<>();
    input.put("name", collection.getName().getValue());
    input.put("description", collection.getDescription().getValue());

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

    Mockito.when(elementService.saveElementWithParent(
            Mockito.argThat((SaveElementDto dto) -> {
              Assert.assertEquals(dto.getName(), collection.getName());
              Assert.assertEquals(dto.getDescription(), collection.getDescription());
              Assert.assertNull(dto.getLink());
              return true;
            }),
            Mockito.argThat((UUID id) -> id.equals(collection.getParentCollection().getId()))
    )).thenReturn(collection);

    Map<String, String> input = new HashMap<>();
    input.put("name", collection.getName().getValue());
    input.put("description", collection.getDescription().getValue());

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

  @Test
  public void shouldReturn422() throws Exception {

    Map<String, String> input = new HashMap<>();

    ObjectMapper objectMapper = new ObjectMapper();
    String inputJson = objectMapper.writeValueAsString(input);

    RequestBuilder request = MockMvcRequestBuilders
            .post("/elements/")
            .content(inputJson)
            .contentType(MediaType.APPLICATION_JSON);

    mockMvc
            .perform(request)
            .andExpect(status().isUnprocessableEntity());
  }
}
