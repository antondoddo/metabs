package com.github.metabs.service;

import com.github.metabs.model.Collection;
import com.github.metabs.model.Element;
import com.github.metabs.model.Tab;
import com.github.metabs.model.dto.SaveElementRequestDto;
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

  public Optional<Element> getElementById(UUID id) {
    return elementRepository.findById(id);
  }

  public Element saveElement(SaveElementRequestDto saveElementRequestDto) {

    Element element;
    if (saveElementRequestDto.getLink() == null) {
      UUID id = UUID.randomUUID();
      element = Collection.createCollection(
          id,
          saveElementRequestDto.getName(),
          saveElementRequestDto.getDescription()
      );
    } else {
      UUID id2 = UUID.randomUUID();
      element = Tab.createTab(
          id2,
          saveElementRequestDto.getName(),
          saveElementRequestDto.getLink(),
          saveElementRequestDto.getDescription()
      );
    }

    return elementRepository.save(element);
  }

  public Element saveElementWithParent(
      SaveElementRequestDto saveElementRequestDto,
      UUID parentCollectionId
  ) throws ParentNotFoundException {
    Optional<Collection> parentCollection = collectionRepository.findById(parentCollectionId);
    if (!parentCollection.isPresent()) {
      throw ParentNotFoundException.parentNotFound();
    }

    Element element;
    if (saveElementRequestDto.getLink() == null) {
      UUID id = UUID.randomUUID();
      element = Collection.createCollectionWithParent(
          id,
          parentCollection.get(),
          saveElementRequestDto.getName(),
          saveElementRequestDto.getDescription()
      );
    } else {
      UUID id2 = UUID.randomUUID();
      element = Tab.createTabWithParent(
          id2,
          parentCollection.get(),
          saveElementRequestDto.getName(),
          saveElementRequestDto.getLink(),
          saveElementRequestDto.getDescription()
      );
    }

    return elementRepository.save(element);
  }
}
