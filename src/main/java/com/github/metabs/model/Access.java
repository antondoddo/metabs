package com.github.metabs.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class Access {

  @Id
  @RelationshipId
  private Long id;

  private Role role;

  @TargetNode
  private User user;

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
