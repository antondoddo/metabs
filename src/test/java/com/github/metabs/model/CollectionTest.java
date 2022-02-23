package com.github.metabs.model;

import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.util.Calendar;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class CollectionTest {

  @Test
  public void createCollectionShouldReturnSameArgumentsAsInjected() {

    Calendar deleted = Calendar.getInstance();
    deleted.setTimeInMillis(3600);
    Calendar created = Calendar.getInstance();
    created.setTimeInMillis(4500);
    Calendar updated = Calendar.getInstance();
    updated.setTimeInMillis(5600);
    Description description = new Description("Description");
    UUID id = UUID.randomUUID();
    Name name = new Name("Collection");
    Collection collection = Collection.createCollection(id, name, deleted, created,
        updated, description);
    Assert.assertEquals(id, collection.getId());
    Assert.assertEquals(name, collection.getName());
    Assert.assertEquals(deleted, collection.getDeleted());
    Assert.assertEquals(created, collection.getCreated());
    Assert.assertEquals(updated, collection.getUpdated());
    Assert.assertEquals(description, collection.getDescription());
  }
}
