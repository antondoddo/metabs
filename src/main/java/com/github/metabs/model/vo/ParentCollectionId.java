package com.github.metabs.model.vo;

import java.util.UUID;
import org.springframework.data.neo4j.core.schema.RelationshipId;

public class ParentCollectionId {

  @RelationshipId
  private final UUID value;

  public ParentCollectionId(UUID value) {
    this.value = value;
  }

  public ParentCollectionId(String value) {
    this.value = UUID.fromString(value);
  }

  public UUID getValue() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    ParentCollectionId objCasted = (ParentCollectionId) obj;
    return this.value.equals(objCasted.value);
  }

  @Override
  public String toString() {
    return this.value.toString();
  }
}
