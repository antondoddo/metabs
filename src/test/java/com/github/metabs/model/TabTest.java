package com.github.metabs.model;

import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class TabTest {

  @Test
  public void createTabShouldReturnSameArgumentsAsInjected() throws Exception {
    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    URL link = ObjectMother.generateRandomLink();
    Description description = ObjectMother.generateRandomDescription();
    Access creator = ObjectMother.generateRandomAccess();
    Tab tab = Tab.createTab(id, name, link, description, creator);

    Assert.assertEquals(id, tab.getId());
    Assert.assertEquals(name, tab.getName());
    Assert.assertEquals(link, tab.getLink());
    Assert.assertEquals(description, tab.getDescription());
    Assert.assertEquals(1, tab.getAccesses().size());
    Assert.assertEquals(creator, tab.getAccesses().get(0));
    Assert.assertEquals(
        tab.getCreated().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
    Assert.assertNull(tab.getUpdated());
    Assert.assertNull(tab.getTrashed());
  }

  @Test
  public void shouldCreateTabWithParent() throws Exception {
    UUID parentId = UUID.randomUUID();
    Name parentName = ObjectMother.generateRandomName();
    Description parentDescription = ObjectMother.generateRandomDescription();
    Access creator = ObjectMother.generateRandomAccess();
    Collection parentCollection = Collection.createCollection(parentId,
        parentName, parentDescription, creator);

    UUID tabId = UUID.randomUUID();
    Name tabName = ObjectMother.generateRandomName();
    URL tabLink = ObjectMother.generateRandomLink();
    Description tabDescription = ObjectMother.generateRandomDescription();

    Tab tab = Tab.createTabWithParent(tabId, parentCollection, tabName,
        tabLink, tabDescription);

    Assert.assertEquals(tabId, tab.getId());
    Assert.assertSame(parentCollection, tab.getParentCollection());
    Assert.assertEquals(tabName, tab.getName());
    Assert.assertEquals(tabLink, tab.getLink());
    Assert.assertEquals(tabDescription, tab.getDescription());
    Assert.assertEquals(
        tab.getCreated().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
    Assert.assertNull(tab.getUpdated());
    Assert.assertNull(tab.getTrashed());
  }

  @Test
  public void shouldRename() throws Exception {
    Tab tab = Tab.createTab(
        UUID.randomUUID(),
        ObjectMother.generateRandomName(),
        ObjectMother.generateRandomLink(),
        ObjectMother.generateRandomDescription(),
        ObjectMother.generateRandomAccess()
    );
    Name randomName = ObjectMother.generateRandomName();
    tab.rename(randomName);
    Assert.assertEquals(randomName, tab.getName());
    Assert.assertEquals(
        tab.getUpdated().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
  }

  @Test
  public void shouldChangeLink() throws Exception {
    Tab tab = Tab.createTab(
        UUID.randomUUID(),
        ObjectMother.generateRandomName(),
        ObjectMother.generateRandomLink(),
        ObjectMother.generateRandomDescription(),
        ObjectMother.generateRandomAccess()
    );
    URL randomLink = ObjectMother.generateRandomLink();
    tab.changeLink(randomLink);
    Assert.assertEquals(randomLink, tab.getLink());
    Assert.assertEquals(
        tab.getUpdated().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
  }

  @Test
  public void shouldChangeDescription() throws Exception {
    Tab tab = Tab.createTab(
        UUID.randomUUID(),
        ObjectMother.generateRandomName(),
        ObjectMother.generateRandomLink(),
        ObjectMother.generateRandomDescription(),
        ObjectMother.generateRandomAccess()
    );
    Description randomDescription = ObjectMother.generateRandomDescription();
    tab.changeDescription(randomDescription);
    Assert.assertEquals(randomDescription, tab.getDescription());
    Assert.assertEquals(
        tab.getUpdated().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
  }

  @Test
  public void shouldBeMovedToBin() throws Exception {
    Tab tab = Tab.createTab(
        UUID.randomUUID(),
        ObjectMother.generateRandomName(),
        ObjectMother.generateRandomLink(),
        ObjectMother.generateRandomDescription(),
        ObjectMother.generateRandomAccess()
    );
    tab.moveToBin();
    Assert.assertEquals(
        tab.getTrashed().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
  }
}
