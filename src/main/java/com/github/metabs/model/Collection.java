package com.github.metabs.model;

import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.time.LocalDateTime;
import java.util.UUID;

public class Collection {

  private final UUID id;
  private final LocalDateTime created;
  private Name name;
  private Description description;
  private LocalDateTime updated;
  private LocalDateTime trashed;


  private Collection(
      UUID id, Name name, Description description,
      LocalDateTime created, LocalDateTime updated, LocalDateTime trashed
  ) {
    this.id = id;
    this.name = name;
    this.trashed = trashed;
    this.created = created;
    this.updated = updated;
    this.description = description;
  }

  public static Collection createCollection(
      UUID id, Name name, Description description) {
    return new Collection(
        id,
        name,
        description,
        LocalDateTime.now(),
        null,
        null);
  }

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