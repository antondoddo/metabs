package com.github.metabs.model;

import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class CollectionTest {

  @Test
  public void createCollectionShouldReturnSameArgumentsAsInjected() throws Exception {
    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    Description description = ObjectMother.generateRandomDescription();

    Collection collection = Collection.createCollection(id, name, description);

    Assert.assertEquals(id, collection.getId());
    Assert.assertEquals(name, collection.getName());
    Assert.assertEquals(description, collection.getDescription());
    Assert.assertEquals(
        collection.getCreated().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
    Assert.assertNull(collection.getUpdated());
    Assert.assertNull(collection.getTrashed());

  }

  @Test
  public void shouldCreateCollectionWithParent() throws Exception {
    UUID parentId = UUID.randomUUID();
    Name parentName = ObjectMother.generateRandomName();
    Description parentDescription = ObjectMother.generateRandomDescription();
    Collection parentCollection = Collection.createCollection(parentId,
        parentName, parentDescription);

    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    Description description = ObjectMother.generateRandomDescription();
    Collection collection = Collection.createCollectionWithParent(id, parentCollection,
        name, description);

    Assert.assertEquals(id, collection.getId());
    Assert.assertEquals(name, collection.getName());
    Assert.assertSame(parentCollection, collection.getParentCollection());
    Assert.assertEquals(description, collection.getDescription());
    Assert.assertEquals(
        collection.getCreated().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
    Assert.assertNull(collection.getUpdated());
    Assert.assertNull(collection.getTrashed());
  }

  @Test
  public void shouldRename() throws Exception {
    Collection collection = Collection.createCollection(
        UUID.randomUUID(),
        ObjectMother.generateRandomName(),
        ObjectMother.generateRandomDescription()
    );

    Name randomName = ObjectMother.generateRandomName();
    collection.rename(randomName);

    Assert.assertEquals(randomName, collection.getName());
    Assert.assertEquals(
        collection.getUpdated().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
  }

  @Test
  public void shouldChangeDescription() throws Exception {
    Collection collection = Collection.createCollection(
        UUID.randomUUID(),
        ObjectMother.generateRandomName(),
        ObjectMother.generateRandomDescription()
    );

    Description randomDescription = ObjectMother.generateRandomDescription();
    collection.changeDescription(randomDescription);

    Assert.assertEquals(randomDescription, collection.getDescription());
    Assert.assertEquals(
        collection.getUpdated().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
  }

  @Test
  public void shouldBeMovedToBin() throws Exception {
    Collection collection = Collection.createCollection(
        UUID.randomUUID(),
        ObjectMother.generateRandomName(),
        ObjectMother.generateRandomDescription()
    );

    collection.moveToBin();

    Assert.assertEquals(
        collection.getTrashed().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
  }
}
