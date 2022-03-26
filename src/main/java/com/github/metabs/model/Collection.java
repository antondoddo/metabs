package com.github.metabs.model;

import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Collection")
public class Collection extends Element {

  private Collection(
      UUID id,
      Collection parentCollection,
      Name name,
      Description description,
      List<Access> accesses,
      LocalDateTime created,
      LocalDateTime updated,
      LocalDateTime trashed
  ) {
    this.id = id;
    this.parentCollection = parentCollection;
    this.name = name;
    this.description = description;
    this.accesses = accesses;
    this.created = created;
    this.updated = updated;
    this.trashed = trashed;
  }

  public static Collection createCollection(
      UUID id,
      Name name,
      Description description,
      Access access
  ) {
    ArrayList<Access> accesses = new ArrayList<>();
    accesses.add(access);
    return new Collection(
        id,
        null,
        name,
        description,
        accesses,
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
        parentCollection.getAccesses(),
        LocalDateTime.now(),
        null,
        null);
  }

  public boolean canBeEditedBy(Access creator) {
    for (Access access : this.accesses) {
      if (
          access.getUser().getId().equals(creator.getUser().getId())
              && access.getRole().hasEditorPermission()
      ) {
        return true;
      }
    }
    return false;
  }
}
