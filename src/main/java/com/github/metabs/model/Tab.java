package com.github.metabs.model;

import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import com.github.metabs.model.vo.converter.UrlConverter;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Tab")
public class Tab extends Element {

  @ConvertWith(converter = UrlConverter.class)
  private URL link;

  private Tab(
      UUID id, Collection parentCollection, Name name, URL link, Description description, LocalDateTime created,
      LocalDateTime updated, LocalDateTime trashed
  ) {
    this.id = id;
    this.parentCollection = parentCollection;
    this.name = name;
    this.link = link;
    this.description = description;
    this.created = created;
    this.updated = updated;
    this.trashed = trashed;

  }

  public static Tab createTab(
      UUID id, Name name, URL link, Description description) {
    return new Tab(
        id,
        null,
        name,
        link,
        description,
        LocalDateTime.now(),
        null,
        null);
  }

  public static Tab createTabWithParent(
      UUID id, Collection parentCollection, Name name, URL link, Description description) {
    return new Tab(
        id,
        parentCollection,
        name,
        link,
        description,
        LocalDateTime.now(),
        null,
        null);
  }

  public URL getLink() {
    return link;
  }

  public void changeLink(URL link) {
    this.link = link;
    this.updated = LocalDateTime.now();
  }

}
