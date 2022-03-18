package com.github.metabs.model.dto;

import org.junit.Assert;
import org.junit.Test;

public class ElementDtoTest {


  @Test
  public void shouldCreateElementDto() {
    ElementDto elementDto = new ElementDto("dto",
        "questo è un dto",
        "https://www.google.com");
    Assert.assertEquals(elementDto.getName(), "dto");
    Assert.assertEquals(elementDto.getDescription(), "questo è un dto");
    Assert.assertEquals(elementDto.getLink(), "https://www.google.com");
  }

  @Test
  public void shouldRename() {
    ElementDto elementDto = new ElementDto("dto",
        "questo è un dto",
        "https://www.google.com");
    elementDto.rename("nome cambiato");
    Assert.assertEquals(elementDto.getName(), "nome cambiato");
  }

  @Test
  public void shouldChangeDescription() {
    ElementDto elementDto = new ElementDto("dto",
        "questo è un dto",
        "https://www.google.com");
    elementDto.changeDescription("descrizione cambiata");
    Assert.assertEquals(elementDto.getDescription(), "descrizione cambiata");
  }

  @Test
  public void shouldChangeLink() {
    ElementDto elementDto = new ElementDto("dto",
        "questo è un dto",
        "https://www.google.com");
    elementDto.changeLink("https://www.facebook.com");
    Assert.assertEquals(elementDto.getLink(), "https://www.facebook.com");
  }
}
