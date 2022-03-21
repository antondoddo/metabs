package com.github.metabs.service;

import com.github.metabs.model.Collection;
import com.github.metabs.model.Element;
import com.github.metabs.model.ObjectMother;
import com.github.metabs.model.Tab;
import com.github.metabs.repository.CollectionRepository;
import com.github.metabs.repository.ElementRepository;
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

    Tab tab = ObjectMother.generateRandomTab();

    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.setName(tab.getName());
    saveElementDto.setLink(tab.getLink());
    saveElementDto.setDescription(tab.getDescription());

    Mockito.when(
        elementRepository.save(Mockito.argThat((Tab tab1) -> {
          Assert.assertEquals(tab.getName(), tab1.getName());
          Assert.assertEquals(tab.getLink(), tab1.getLink());
          Assert.assertEquals(tab.getDescription(), tab1.getDescription());
          return true;
        })
        )
    ).thenReturn(tab);

    Element savedTab = elementService.saveElement(saveElementDto);
    Tab savedTabCasted = (Tab) savedTab;

    Assert.assertEquals(tab.getName(), savedTabCasted.getName());
    Assert.assertEquals(tab.getLink(), savedTabCasted.getLink());
    Assert.assertEquals(tab.getDescription(), savedTabCasted.getDescription());
    Assert.assertEquals(tab.getCreated(), savedTabCasted.getCreated());
    Assert.assertNull(tab.getUpdated());
    Assert.assertNull(tab.getTrashed());

  }

  @Test
  public void shouldSaveTabWithParent() throws Exception {

    Tab tab = ObjectMother.generateRandomTabWithParent();

    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.setName(tab.getName());
    saveElementDto.setLink(tab.getLink());
    saveElementDto.setDescription(tab.getDescription());

    Mockito.when(
        collectionRepository.findById(Mockito.argThat((UUID uuid) -> {
          Assert.assertEquals(tab.getParentCollection().getId(),
              uuid
          );
          return true;
        })
        )
    ).thenReturn(Optional.of(tab.getParentCollection()));

    Mockito.when(
        elementRepository.save(Mockito.argThat((Tab tab1) -> {
          Assert.assertEquals(tab.getName(), tab1.getName());
          Assert.assertEquals(tab.getParentCollection().getId(), tab1.getParentCollection().getId()
          );
          Assert.assertEquals(tab.getLink(), tab1.getLink());
          Assert.assertEquals(tab.getDescription(), tab1.getDescription());
          return true;
        })
        )
    ).thenReturn(tab);

    Element savedTab = elementService.saveElementWithParent(saveElementDto,
        tab.getParentCollection().getId()
    );
    Tab savedTabCasted = (Tab) savedTab;

    Assert.assertEquals(tab.getName(), savedTabCasted.getName());
    Assert.assertEquals(tab.getParentCollection().getId(),
        savedTabCasted.getParentCollection().getId()
    );
    Assert.assertEquals(tab.getLink(), savedTabCasted.getLink());
    Assert.assertEquals(tab.getDescription(), savedTabCasted.getDescription());
    Assert.assertEquals(tab.getCreated(), savedTabCasted.getCreated());
    Assert.assertNull(tab.getUpdated());
    Assert.assertNull(tab.getTrashed());
  }

  @Test
  public void shouldSaveCollection() throws Exception {

    Collection collection = ObjectMother.generateRandomCollection();

    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.setName(collection.getName());
    saveElementDto.setDescription(collection.getDescription());

    Mockito.when(
        elementRepository.save(Mockito.argThat((Collection collection1) -> {
          Assert.assertEquals(collection.getName(), collection1.getName());
          Assert.assertEquals(collection.getDescription(), collection1.getDescription());
          return true;
        })
        )
    ).thenReturn(collection);

    Element savedCollection = elementService.saveElement(saveElementDto);
    Collection savedCollectionCasted = (Collection) savedCollection;

    Assert.assertEquals(collection.getName(), savedCollectionCasted.getName());
    Assert.assertEquals(collection.getDescription(), savedCollectionCasted.getDescription());
    Assert.assertEquals(collection.getCreated(), savedCollectionCasted.getCreated());
    Assert.assertNull(collection.getUpdated());
    Assert.assertNull(collection.getTrashed());

  }

  @Test
  public void shouldSaveCollectionWithParent() throws Exception {

    Collection collection = ObjectMother.generateRandomCollectionWithParent();

    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.setName(collection.getName());
    saveElementDto.setDescription(collection.getDescription());

    Mockito.when(
        collectionRepository.findById(Mockito.argThat((UUID uuid) -> {
          Assert.assertEquals(collection.getParentCollection().getId(),
              uuid
          );
          return true;
        })
        )
    ).thenReturn(Optional.of(collection.getParentCollection()));

    Mockito.when(
        elementRepository.save(Mockito.argThat((Collection collection1) -> {
          Assert.assertEquals(collection.getName(), collection1.getName());
          Assert.assertEquals(collection.getParentCollection().getId(),
              collection1.getParentCollection().getId()
          );
          Assert.assertEquals(collection.getDescription(), collection1.getDescription());
          return true;
        })
        )
    ).thenReturn(collection);

    Element savedCollection = elementService.saveElementWithParent(saveElementDto,
        collection.getParentCollection().getId()
    );
    Collection savedCollectionCasted = (Collection) savedCollection;

    Assert.assertEquals(collection.getName(), savedCollectionCasted.getName());
    Assert.assertEquals(collection.getParentCollection().getId(),
        savedCollectionCasted.getParentCollection().getId()
    );
    Assert.assertEquals(collection.getDescription(), savedCollectionCasted.getDescription());
    Assert.assertEquals(collection.getCreated(), savedCollectionCasted.getCreated());
    Assert.assertNull(collection.getUpdated());
    Assert.assertNull(collection.getTrashed());
  }

}

