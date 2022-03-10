package com.github.metabs.model.vo;

import com.github.metabs.model.exception.NameException;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class ParentCollectionIdTest {

  @Test
  public void shouldReturnParentCollectionIdWithUuidParameter() {
    UUID id = UUID.randomUUID();
    ParentCollectionId parentCollectionId = new ParentCollectionId(id);
    Assert.assertSame(parentCollectionId.getValue(), id);
  }

  @Test
  public void shouldReturnParentCollectionIdWithStringParameter() {
    UUID id = UUID.randomUUID();
    ParentCollectionId parentCollectionId = new ParentCollectionId(id.toString());
    Assert.assertEquals(parentCollectionId.getValue().toString(), id.toString());
  }

  @Test
  public void shouldBeEquals() throws NameException {
    UUID id = UUID.randomUUID();
    ParentCollectionId parentCollectionId = new ParentCollectionId(id.toString());
    ParentCollectionId parentCollectionId2 = new ParentCollectionId(id);
    Assert.assertEquals(parentCollectionId, parentCollectionId2);
  }

  @Test
  public void shouldReturnSameString() {
    UUID id = UUID.randomUUID();
    ParentCollectionId parentCollectionId = new ParentCollectionId(id.toString());
    Assert.assertEquals(parentCollectionId.toString(), id.toString());
  }
}
