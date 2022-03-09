package com.github.metabs.controller;

import com.github.metabs.model.Collection;
import com.github.metabs.model.Element;
import com.github.metabs.model.Tab;
import com.github.metabs.model.dto.ElementDTO;
import com.github.metabs.model.exception.DescriptionException;
import com.github.metabs.model.exception.NameException;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import com.github.metabs.repository.CollectionRepository;
import com.github.metabs.repository.ElementRepository;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("elements")
public class CollectionController {

  @Autowired
  ElementRepository elementRepository;

  @Autowired
  CollectionRepository collectionRepository;

  @GetMapping("/{id}")
  public Optional<Element> getElementById(@PathVariable UUID id) {
    return elementRepository.findElementWithParent(id);
  }

  @PostMapping("/")
  @ResponseBody
  public Element saveElement(@RequestBody ElementDTO elementDTO) throws
      DescriptionException,
      NameException,
      MalformedURLException {
    Element element;
    if (elementDTO.getLink() == null) {
      UUID id = UUID.randomUUID();
      element = Collection.createCollection(
          id,
          new Name(elementDTO.getName()),
          new Description(elementDTO.getDescription())
      );

    } else {
      UUID id2 = UUID.randomUUID();
      element = Tab.createTab(id2, new Name(elementDTO.getName()),
          new URL(elementDTO.getLink()), new Description(elementDTO.getDescription()));

    }
    return elementRepository.save(element);
  }

  @PostMapping("/{id}")
  @ResponseBody
  public Element saveElementWithParent(@RequestBody ElementDTO elementDTO,
                                       @PathVariable("id") UUID parentCollectionId) throws
      DescriptionException,
      NameException,
      MalformedURLException {

    Optional<Collection> parentCollection = collectionRepository.findCollectionWithParent(parentCollectionId);
    if (!parentCollection.isPresent()) {
      return null; // TODO RETURN 404
    }

    Element element;
    if (elementDTO.getLink() == null) {
      UUID id = UUID.randomUUID();
      element = Collection.createCollectionWithParent(
          id,
          parentCollection.get(),
          new Name(elementDTO.getName()),
          new Description(elementDTO.getDescription())
      );

    } else {
      UUID id2 = UUID.randomUUID();
      element = Tab.createTabWithParent(id2, parentCollection.get(), new Name(elementDTO.getName()),
          new URL(elementDTO.getLink()), new Description(elementDTO.getDescription()));
    }
    return elementRepository.save(element);
  }
}
