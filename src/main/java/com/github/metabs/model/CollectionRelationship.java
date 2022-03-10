package com.github.metabs.model;

import java.util.UUID;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.TargetNode;


public class CollectionRelationship {

  @RelationshipId
  public UUID id;

  @TargetNode
  public Collection collection;
}
