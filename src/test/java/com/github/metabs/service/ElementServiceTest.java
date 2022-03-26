package com.github.metabs.service;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

import com.github.metabs.model.Access;
import com.github.metabs.model.Collection;
import com.github.metabs.model.Element;
import com.github.metabs.model.ObjectMother;
import com.github.metabs.model.Tab;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import com.github.metabs.repository.CollectionRepository;
import com.github.metabs.repository.ElementRepository;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ElementServiceTest {

  @Mock
  private ElementRepository elementRepository;

  @Mock
  private CollectionRepository collectionRepository;

  @InjectMocks
  private ElementService elementService;

  @Test
  public void shouldReturnElementById() throws Exception {
    Tab tab = ObjectMother.generateRandomTab();
    Mockito.when(
        elementRepository.findById(tab.getId())
    ).thenReturn(Optional.of(tab));
    Optional<Element> optionalReturnedTab = elementService.getElementById(tab.getId());

    Assert.assertTrue(optionalReturnedTab.isPresent());
    Assert.assertSame(tab, optionalReturnedTab.get());
  }

  @Test
  public void shouldSaveTab() throws Exception {

    Name name = ObjectMother.generateRandomName();
    URL link = ObjectMother.generateRandomLink();
    Description description = ObjectMother.generateRandomDescription();
    Access creator = ObjectMother.generateAdminAccess();

    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.setName(name);
    saveElementDto.setLink(link);
    saveElementDto.setDescription(description);
    saveElementDto.setCreator(creator);

    Mockito.when(
        elementRepository.save(Mockito.argThat((Tab tab1) -> {
          Assert.assertEquals(name, tab1.getName());
          Assert.assertEquals(link, tab1.getLink());
          Assert.assertEquals(description, tab1.getDescription());
          Assert.assertEquals(creator, tab1.getAccesses().get(0));
          Assert.assertEquals(1, tab1.getAccesses().size());
          return true;
        }))
    ).thenAnswer(returnsFirstArg());

    Tab tab = (Tab) elementService.saveElement(saveElementDto);

    Assert.assertEquals(name, tab.getName());
    Assert.assertEquals(link, tab.getLink());
    Assert.assertEquals(description, tab.getDescription());
    Assert.assertEquals(creator, tab.getAccesses().get(0));
    Assert.assertEquals(1, tab.getAccesses().size());
    Assert.assertEquals(
        tab.getCreated().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
    Assert.assertNull(tab.getUpdated());
    Assert.assertNull(tab.getTrashed());

  }

  @Test
  public void shouldSaveTabWithParent() throws Exception {
    Name name = ObjectMother.generateRandomName();
    URL link = ObjectMother.generateRandomLink();
    Description description = ObjectMother.generateRandomDescription();
    Access creator = ObjectMother.generateAdminAccess();

    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.setName(name);
    saveElementDto.setLink(link);
    saveElementDto.setDescription(description);
    saveElementDto.setCreator(creator);

    Collection parent = ObjectMother.generateRandomCollection(creator);

    Mockito.when(
        collectionRepository.findById(Mockito.argThat((UUID uuid) -> {
          Assert.assertEquals(parent.getId(),
              uuid
          );
          return true;
        }))
    ).thenReturn(Optional.of(parent));

    Mockito.when(
        elementRepository.save(Mockito.argThat((Tab tab) -> {
          Assert.assertEquals(name, tab.getName());
          Assert.assertEquals(parent.getId(), tab.getParentCollection().getId()
          );
          Assert.assertEquals(link, tab.getLink());
          Assert.assertEquals(description, tab.getDescription());
          Assert.assertEquals(creator, tab.getAccesses().get(0));
          Assert.assertEquals(1, tab.getAccesses().size());
          return true;
        }))
    ).thenAnswer(returnsFirstArg());

    Tab tab = (Tab) elementService.saveElementWithParent(
        saveElementDto,
        parent.getId()
    );

    Assert.assertEquals(name, tab.getName());
    Assert.assertEquals(parent.getId(), tab.getParentCollection().getId());
    Assert.assertEquals(link, tab.getLink());
    Assert.assertEquals(description, tab.getDescription());
    Assert.assertEquals(creator, tab.getAccesses().get(0));
    Assert.assertEquals(1, tab.getAccesses().size());
    Assert.assertEquals(
        tab.getCreated().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
    Assert.assertNull(tab.getUpdated());
    Assert.assertNull(tab.getTrashed());
  }

  @Test
  public void shouldSaveCollection() throws Exception {

    Name name = ObjectMother.generateRandomName();
    Description description = ObjectMother.generateRandomDescription();
    Access creator = ObjectMother.generateAdminAccess();

    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.setName(name);
    saveElementDto.setDescription(description);
    saveElementDto.setCreator(creator);

    Mockito.when(
        elementRepository.save(Mockito.argThat((Collection collection1) -> {
          Assert.assertEquals(name, collection1.getName());
          Assert.assertEquals(description, collection1.getDescription());
          Assert.assertEquals(creator,
              collection1.getAccesses().get(0)
          );
          Assert.assertEquals(1,
              collection1.getAccesses().size()
          );
          return true;
        }))
    ).then(returnsFirstArg());

    Collection collection = (Collection) elementService.saveElement(saveElementDto);

    Assert.assertEquals(name, collection.getName());
    Assert.assertEquals(description, collection.getDescription());
    Assert.assertEquals(creator, collection.getAccesses().get(0));
    Assert.assertEquals(1, collection.getAccesses().size());
    Assert.assertEquals(
        collection.getCreated().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
    Assert.assertNull(collection.getUpdated());
    Assert.assertNull(collection.getTrashed());

  }

  @Test
  public void shouldSaveCollectionWithParent() throws Exception {

    Name name = ObjectMother.generateRandomName();
    Description description = ObjectMother.generateRandomDescription();
    Access creator = ObjectMother.generateAdminAccess();

    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.setName(name);
    saveElementDto.setDescription(description);
    saveElementDto.setCreator(creator);

    Collection parent = ObjectMother.generateRandomCollection(creator);

    Mockito.when(
        collectionRepository.findById(Mockito.argThat((UUID uuid) -> {
          Assert.assertEquals(parent.getId(),
              uuid
          );
          return true;
        }))
    ).thenReturn(Optional.of(parent));

    Mockito.when(
        elementRepository.save(Mockito.argThat((Collection collection1) -> {
          Assert.assertEquals(name, collection1.getName());
          Assert.assertEquals(parent.getId(),
              collection1.getParentCollection().getId()
          );
          Assert.assertEquals(description, collection1.getDescription());
          Assert.assertEquals(creator, collection1.getAccesses().get(0));
          Assert.assertEquals(1, collection1.getAccesses().size());
          return true;
        }))
    ).thenAnswer(returnsFirstArg());

    Collection collection = (Collection) elementService.saveElementWithParent(
        saveElementDto,
        parent.getId()
    );


    Assert.assertEquals(name, collection.getName());
    Assert.assertEquals(parent.getId(),
        collection.getParentCollection().getId()
    );
    Assert.assertEquals(description, collection.getDescription());
    Assert.assertEquals(creator, collection.getAccesses().get(0));
    Assert.assertEquals(1, collection.getAccesses().size());
    Assert.assertEquals(
        collection.getCreated().toEpochSecond(ZoneOffset.UTC),
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        3
    );
    Assert.assertNull(collection.getUpdated());
    Assert.assertNull(collection.getTrashed());
  }
}

