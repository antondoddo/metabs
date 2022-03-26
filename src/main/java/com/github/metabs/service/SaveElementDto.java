package com.github.metabs.service;

import com.github.metabs.model.Access;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.net.URL;


public class SaveElementDto {

  private Name name;
  private URL link;
  private Description description;
  private Access creator;


  public SaveElementDto() {
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public Description getDescription() {
    return description;
  }

  public void setDescription(Description description) {
    this.description = description;
  }

  public URL getLink() {
    return link;
  }

  public void setLink(URL link) {
    this.link = link;
  }

  public Access getCreator() {
    return creator;
  }

  public void setCreator(Access creator) {
    this.creator = creator;
  }
}
