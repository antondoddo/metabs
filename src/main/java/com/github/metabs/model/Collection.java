package com.github.metabs.model;

import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Collection")
public class Collection extends Element {

  private Collection(
      UUID id,
      Collection parentCollection,
      Name name,
      Description description,
      LocalDateTime created,
      LocalDateTime updated,
      LocalDateTime trashed
  ) {
    this.id = id;
    this.parentCollection = parentCollection;
    this.name = name;
    this.description = description;
    this.created = created;
    this.updated = updated;
    this.trashed = trashed;
  }

  public static Collection createCollection(
      UUID id,
      Name name,
      Description description
  ) {
    return new Collection(
        id,
        null,
        name,
        description,
        LocalDateTime.now(),
        null,
        null);
  }

  public static Collection createCollectionWithParent(
      UUID id,
      Collection parentCollection,
      Name name,
      Description description
  ) {
    return new Collection(
        id,
        parentCollection,
        name,
        description,
        LocalDateTime.now(),
        null,
        null);
  }
}
