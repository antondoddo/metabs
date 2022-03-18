package com.github.metabs.controller;

import com.github.metabs.model.ObjectMother;
import com.github.metabs.model.Tab;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import com.github.metabs.repository.ElementRepository;
import com.github.metabs.service.ElementService;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@WebMvcTest(ElementController.class)
public class ElementControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  ElementService elementService;

  @MockBean
  ElementRepository elementRepository;

  @Test
  public void shouldReturnElementById() throws Exception {

    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    URL link = ObjectMother.generateRandomLink();
    Description description = ObjectMother.generateRandomDescription();
    Tab tab = Tab.createTab(id, name, link, description);
    Mockito.when(elementService.getElementById(id)).thenReturn(Optional.of(tab));
    String uri = "/elements/" + id;
    RequestBuilder request = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(request).andReturn();
    MockHttpServletResponse response = result.getResponse();
    Assert.assertEquals(HttpStatus.OK, response.getStatus());

    String outputJson = result.getResponse().getContentAsString();
  }
}
