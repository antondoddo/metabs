package com.github.metabs.controller;

import com.github.metabs.model.Collection;
import com.github.metabs.model.Element;
import com.github.metabs.model.Tab;
import com.github.metabs.model.exception.DescriptionException;
import com.github.metabs.model.exception.NameException;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import com.github.metabs.repository.ElementRepository;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("collections")
public class CollectionController {

  @Autowired
  ElementRepository elementRepository;

  @GetMapping("/{id}")
  public Optional<Element> getElementById(@PathVariable UUID id) {
    return elementRepository.findById(id);
  }

  @PostMapping("/")
  public Optional<Element> saveElement() throws
      DescriptionException,
      NameException,
      MalformedURLException {
    UUID id = UUID.randomUUID();
    Collection collection = Collection.createCollection(
        id,
        new Name("Damiano"),
        new Description("asad")
    );
    elementRepository.save(collection);
    UUID id2 = UUID.randomUUID();
    Tab tab = Tab.createTab(id2, new Name("Antonio"),
        new URL("https://google.com"), new Description("ciao"));
    elementRepository.save(tab);
    Optional<Element> element = elementRepository.findById(id2);
    return element;
  }
}
