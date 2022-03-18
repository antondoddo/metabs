package com.github.metabs.model.dto;

public class ElementDto {

  private String name;
  private String description;
  private String link;


  public ElementDto(String name, String description, String link) {
    this.name = name;
    this.description = description;
    this.link = link;
  }

  public String getName() {
    return name;
  }

  public void rename(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void changeDescription(String description) {
    this.description = description;
  }

  public String getLink() {
    return link;
  }

  public void changeLink(String link) {
    this.link = link;
  }
}
