package com.github.metabs.controller;

import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jClient.MappingSpec;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

  @Mock
  Neo4jClient neo4jClient;
  @InjectMocks
  HomeController homeController;
  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
  }

  @Test
  public void shouldReturnHealthyResponse() throws Exception {

    MappingSpec mappingSpec = Mockito.mock(MappingSpec.class);
    Mockito.when(mappingSpec.one()).thenReturn(Optional.of(1));

    Neo4jClient.UnboundRunnableSpec spec = Mockito.mock(Neo4jClient.UnboundRunnableSpec.class);
    Mockito.when(spec.fetchAs(Integer.class)).thenReturn(mappingSpec);

    Mockito.when(neo4jClient.query("RETURN 1")).thenReturn(spec);


    MockHttpServletResponse response = mockMvc
        .perform(MockMvcRequestBuilders.get("/"))
        .andReturn()
        .getResponse();

    Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    Assert.assertEquals(
        "{\"neo4j\":\"healthy\",\"name\":\"metabs\"}",
        response.getContentAsString()
    );
  }

  @Test
  public void shouldReturnUnhealthyResponse() throws Exception {

    Mockito.when(
        neo4jClient.query("RETURN 1")
    ).thenThrow(new RuntimeException("ops!"));


    MockHttpServletResponse response = mockMvc
        .perform(MockMvcRequestBuilders.get("/"))
        .andReturn()
        .getResponse();

    Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    Assert.assertEquals(
        "{\"neo4j\":\"not healthy\",\"name\":\"metabs\"}",
        response.getContentAsString()
    );
  }
}
