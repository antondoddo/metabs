package com.github.metabs.service;

import com.github.metabs.model.Collection;
import com.github.metabs.model.Element;
import com.github.metabs.model.Tab;
import com.github.metabs.repository.CollectionRepository;
import com.github.metabs.repository.ElementRepository;
import com.github.metabs.service.exception.CreatorNotAllowedException;
import com.github.metabs.service.exception.ParentNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElementService {

  @Autowired
  private ElementRepository elementRepository;

  @Autowired
  private CollectionRepository collectionRepository;

  public ElementService() {
  }

  public Optional<Element> getElementById(UUID id) {
    return elementRepository.findById(id);
  }

  public Element saveElement(SaveElementDto dto) {

    Element element;
    UUID id = UUID.randomUUID();

    if (dto.getLink() == null) {
      element = Collection.createCollection(
          id,
          dto.getName(),
          dto.getDescription(),
          dto.getCreator()
      );
    } else {
      element = Tab.createTab(
          id,
          dto.getName(),
          dto.getLink(),
          dto.getDescription(),
          dto.getCreator()
      );
    }
    return elementRepository.save(element);
  }

  public Element saveElementWithParent(
      SaveElementDto dto,
      UUID parentCollectionId
  ) throws ParentNotFoundException, CreatorNotAllowedException {
    Optional<Collection> parentCollectionOptional = collectionRepository.findById(
        parentCollectionId
    );
    if (!parentCollectionOptional.isPresent()) {
      throw ParentNotFoundException.parentNotFound();
    }
    Element element;
    UUID id = UUID.randomUUID();
    Collection parentCollection = parentCollectionOptional.get();

    if (!parentCollection.canBeEditedBy(dto.getCreator())) {
      throw CreatorNotAllowedException.notAllowed();
    }

    if (dto.getLink() == null) {
      element = Collection.createCollectionWithParent(
          id,
          parentCollection,
          dto.getName(),
          dto.getDescription()
      );
    } else {
      element = Tab.createTabWithParent(
          id,
          parentCollectionOptional.get(),
          dto.getName(),
          dto.getLink(),
          dto.getDescription()
      );
    }
    return elementRepository.save(element);
  }

  public void trashElementById(UUID id) {
    elementRepository.deleteById(id);
  }
}
