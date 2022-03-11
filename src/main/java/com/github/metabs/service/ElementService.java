package com.github.metabs.service;

import com.github.metabs.model.Collection;
import com.github.metabs.model.Element;
import com.github.metabs.model.Tab;
import com.github.metabs.repository.CollectionRepository;
import com.github.metabs.repository.ElementRepository;
import com.github.metabs.service.dto.SaveElementDto;
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

  public Optional<Element> getElementById(UUID id) {
    return elementRepository.findById(id);
  }

  public Element saveElement(SaveElementDto dto) {

    UUID id = UUID.randomUUID();
    Element element;

    if (!dto.getLink().isPresent()) {
      element = Collection.createCollection(
              id,
              dto.getName(),
              dto.getDescription()
      );
    } else {
      element = Tab.createTab(
              id,
              dto.getName(),
              dto.getLink().get(),
              dto.getDescription()
      );
    }

    return elementRepository.save(element);
  }

  public Element saveElementWithParent(
          UUID parentCollectionId,
          SaveElementDto dto
  ) throws ParentNotFoundException {

    Optional<Collection> parentCollection = collectionRepository.findById(parentCollectionId);
    if (!parentCollection.isPresent()) {
      throw ParentNotFoundException.parentNotFound();
    }

    Element element;
    UUID id = UUID.randomUUID();

    if (!dto.getLink().isPresent()) {
      element = Collection.createCollectionWithParent(
              id,
              parentCollection.get(),
              dto.getName(),
              dto.getDescription()
      );
    } else {
      element = Tab.createTabWithParent(
              id,
              parentCollection.get(),
              dto.getName(),
              dto.getLink().get(),
              dto.getDescription()
      );
    }

    return elementRepository.save(element);
  }
}
