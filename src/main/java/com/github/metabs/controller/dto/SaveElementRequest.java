package com.github.metabs.controller.dto;

public class SaveElementRequest {

  private final String name;
  private final String description;
  private final String link;


  public SaveElementRequest(String name, String description, String link) {
    this.name = name;
    this.description = description;
    this.link = link;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getLink() {
    return link;
  }
}