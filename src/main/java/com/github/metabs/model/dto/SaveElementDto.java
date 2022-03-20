package com.github.metabs.model.dto;

import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.net.URL;


public class SaveElementDto {

  private Name name;
  private URL link;
  private Description description;


  public SaveElementDto() {
  }

  public Name getName() {
    return name;
  }

  public void rename(Name name) {
    this.name = name;
  }

  public Description getDescription() {
    return description;
  }

  public void changeDescription(Description description) {
    this.description = description;
  }

  public URL getLink() {
    return link;
  }

  public void changeLink(URL link) {
    this.link = link;
  }
}
