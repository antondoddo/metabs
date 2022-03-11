package com.github.metabs.service;

import com.github.metabs.model.Collection;
import com.github.metabs.model.Element;
import com.github.metabs.model.Tab;
import com.github.metabs.model.dto.ElementDto;
import com.github.metabs.model.exception.DescriptionException;
import com.github.metabs.model.exception.NameException;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import com.github.metabs.repository.CollectionRepository;
import com.github.metabs.repository.ElementRepository;
import com.github.metabs.service.exception.ParentNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
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

  public Element saveElement(ElementDto elementDto) throws
      DescriptionException,
      NameException,
      MalformedURLException {

    Element element;
    if (elementDto.getLink() == null) {
      UUID id = UUID.randomUUID();
      element = Collection.createCollection(
          id,
          new Name(elementDto.getName()),
          new Description(elementDto.getDescription())
      );
    } else {
      UUID id2 = UUID.randomUUID();
      element = Tab.createTab(
          id2,
          new Name(elementDto.getName()),
          new URL(elementDto.getLink()),
          new Description(elementDto.getDescription())
      );
    }

    return elementRepository.save(element);
  }

  public Element saveElementWithParent(
      ElementDto elementDto,
      UUID parentCollectionId
  ) throws
      DescriptionException,
      NameException,
      MalformedURLException,
      ParentNotFoundException {

    Optional<Collection> parentCollection = collectionRepository.findById(parentCollectionId);
    if (!parentCollection.isPresent()) {
      throw ParentNotFoundException.parentNotFound();
    }

    Element element;
    if (elementDto.getLink() == null) {
      UUID id = UUID.randomUUID();
      element = Collection.createCollectionWithParent(
          id,
          parentCollection.get(),
          new Name(elementDto.getName()),
          new Description(elementDto.getDescription())
      );
    } else {
      UUID id2 = UUID.randomUUID();
      element = Tab.createTabWithParent(
          id2,
          parentCollection.get(),
          new Name(elementDto.getName()),
          new URL(elementDto.getLink()),
          new Description(elementDto.getDescription())
      );
    }

    return elementRepository.save(element);
  }
}
