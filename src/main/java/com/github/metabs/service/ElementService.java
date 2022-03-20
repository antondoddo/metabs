package com.github.metabs.service;

import com.github.metabs.model.Collection;
import com.github.metabs.model.Element;
import com.github.metabs.model.Tab;
import com.github.metabs.model.dto.SaveElementDto;
import com.github.metabs.repository.CollectionRepository;
import com.github.metabs.repository.ElementRepository;
import com.github.metabs.service.exception.ParentNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElementService {

  @Autowired
  ElementRepository elementRepository;

  @Autowired
  CollectionRepository collectionRepository;

  public ElementService() {
  }

  public Optional<Element> getElementById(UUID id) {
    return elementRepository.findById(id);
  }

  public Element saveElement(SaveElementDto dto) {

    com.github.metabs.model.Element element;
    if (dto.getLink() == null) {
      UUID id = UUID.randomUUID();
      element = Collection.createCollection(
          id,
          dto.getName(),
          dto.getDescription()
      );
    } else {
      UUID id2 = UUID.randomUUID();
      element = Tab.createTab(
          id2,
          dto.getName(),
          dto.getLink(),
          dto.getDescription()
      );
    }
    return elementRepository.save(element);
  }

  public com.github.metabs.model.Element saveElementWithParent(
      SaveElementDto dto,
      UUID parentCollectionId
  ) throws ParentNotFoundException {
    Optional<Collection> parentCollection = collectionRepository.findById(parentCollectionId);
    if (!parentCollection.isPresent()) {
      throw ParentNotFoundException.parentNotFound();
    }
    com.github.metabs.model.Element element;
    if (dto.getLink() == null) {
      UUID id = UUID.randomUUID();
      element = Collection.createCollectionWithParent(
          id,
          parentCollection.get(),
          dto.getName(),
          dto.getDescription()
      );
    } else {
      UUID id2 = UUID.randomUUID();
      element = Tab.createTabWithParent(
          id2,
          parentCollection.get(),
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
