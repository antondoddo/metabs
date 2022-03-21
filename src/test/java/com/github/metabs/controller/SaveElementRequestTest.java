package com.github.metabs.controller;

import org.junit.Assert;
import org.junit.Test;

public class SaveElementRequestTest {

  @Test
  public void shouldCreateElementDto() {
    SaveElementRequest saveElementRequest = new SaveElementRequest("dto",
        "questo è un dto",
        "https://www.google.com");
    Assert.assertEquals(saveElementRequest.getName(), "dto");
    Assert.assertEquals(saveElementRequest.getDescription(), "questo è un dto");
    Assert.assertEquals(saveElementRequest.getLink(), "https://www.google.com");
  }

  @Test
  public void shouldRename() {
    SaveElementRequest saveElementRequest = new SaveElementRequest("dto",
        "questo è un dto",
        "https://www.google.com");
    saveElementRequest.setName("nome cambiato");
    Assert.assertEquals(saveElementRequest.getName(), "nome cambiato");
  }

  @Test
  public void shouldChangeDescription() {
    SaveElementRequest saveElementRequest = new SaveElementRequest("dto",
        "questo è un dto",
        "https://www.google.com");
    saveElementRequest.setDescription("descrizione cambiata");
    Assert.assertEquals(saveElementRequest.getDescription(), "descrizione cambiata");
  }

  @Test
  public void shouldChangeLink() {
    SaveElementRequest saveElementRequest = new SaveElementRequest("dto",
        "questo è un dto",
        "https://www.google.com");
    saveElementRequest.setLink("https://www.facebook.com");
    Assert.assertEquals(saveElementRequest.getLink(), "https://www.facebook.com");
  }
}
