package com.github.metabs.model;

import java.util.UUID;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("User")
public class User {

  @Id
  private UUID id;
  private String firstname;
  private String lastname;

  public User(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
