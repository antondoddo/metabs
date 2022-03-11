package com.github.metabs.service.dto;

import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.net.URL;
import java.util.Optional;

public class SaveElementDto {

  private final Name name;
  private final Description description;
  private final Optional<URL> link;


  public SaveElementDto(Name name, Description description, Optional<URL> link) {
    this.name = name;
    this.description = description;
    this.link = link;
  }

  public Name getName() {
    return name;
  }

  public Description getDescription() {
    return description;
  }

  public Optional<URL> getLink() {
    return link;
  }
}