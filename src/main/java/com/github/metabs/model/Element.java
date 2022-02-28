package com.github.metabs.model;

import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.neo4j.core.schema.Id;

public abstract class Element implements Node {

  @Id
  protected UUID id;
  protected Name name;
  protected Description description;
  protected LocalDateTime created;
  protected LocalDateTime updated;
  protected LocalDateTime trashed;


  public UUID getId() {
    return id;
  }

  public Name getName() {
    return name;
  }

  public LocalDateTime getTrashed() {
    return trashed;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public LocalDateTime getUpdated() {
    return updated;
  }

  public Description getDescription() {
    return description;
  }

  public void rename(Name name) {
    this.name = name;
    this.updated = LocalDateTime.now();
  }

  public void changeDescription(Description description) {
    this.description = description;
    this.updated = LocalDateTime.now();
  }

  public void moveToBin() {
    this.trashed = LocalDateTime.now();
  }
}
