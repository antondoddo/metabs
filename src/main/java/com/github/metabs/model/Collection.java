package com.github.metabs.model;

import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.util.Calendar;
import java.util.UUID;


public class Collection {

  private UUID id;
  private Name name;
  //private AccessBy accessBy;
  private Calendar deleted;
  private Calendar created;
  private Calendar updated;
  private Description description;

  private Collection(
      UUID id, Name name, Calendar deleted, Calendar created,
      Calendar updated, Description description) {
    this.id = id;
    this.name = name;
    this.deleted = deleted;
    this.created = created;
    this.updated = updated;
    this.description = description;
  }

  public static Collection createCollection(
      UUID id, Name name, Calendar deleted, Calendar created,
      Calendar updated, Description description) {
    return new Collection(
        id,
        name,
        deleted,
        created,
        updated,
        description);
  }

  public UUID getId() {
    return id;
  }

  public Name getName() {
    return name;
  }

  public Calendar getDeleted() {
    return deleted;
  }

  public Calendar getCreated() {
    return created;
  }

  public Calendar getUpdated() {
    return updated;
  }

  public Description getDescription() {
    return description;
  }
}
