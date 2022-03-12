package com.github.metabs.model;

import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import com.github.metabs.model.vo.converter.DescriptionConverter;
import com.github.metabs.model.vo.converter.NameConverter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

@org.springframework.data.neo4j.core.schema.Node("Element")
public abstract class Element {

  @Id
  protected UUID id;

  @Relationship(type = "HAS_PARENT", direction = Relationship.Direction.OUTGOING)
  protected Collection parentCollection;

  @ConvertWith(converter = NameConverter.class)
  protected Name name;

  @ConvertWith(converter = DescriptionConverter.class)
  protected Description description;

  @Relationship(type = "ACCESS_BY", direction = Relationship.Direction.INCOMING)
  protected List<Access> accesses;

  protected LocalDateTime created;
  protected LocalDateTime updated;
  protected LocalDateTime trashed;

  public UUID getId() {
    return id;
  }


  public Collection getParentCollection() {
    return parentCollection;
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
