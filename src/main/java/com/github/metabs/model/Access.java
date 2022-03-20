package com.github.metabs.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class Access {

  private final Role role;
  @TargetNode
  private final User user;
  @Id
  @RelationshipId
  private Long id;

  public Access(Role role, User user) {
    this.role = role;
    this.user = user;
  }

  public Role getRole() {
    return role;
  }

  public User getUser() {
    return user;
  }
}
