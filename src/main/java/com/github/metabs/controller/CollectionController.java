package com.github.metabs.controller;

import com.github.metabs.model.Collection;
import com.github.metabs.model.Element;
import com.github.metabs.model.exception.DescriptionException;
import com.github.metabs.model.exception.NameException;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import com.github.metabs.repository.ElementRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("collections")
public class CollectionController {

  @Autowired
  ElementRepository elementRepository;

  @GetMapping("/{id}")
  public Mono<Collection> getCollectionById(@PathVariable UUID id) {
    return elementRepository.findById(id);
  }

  @PostMapping("/")
  public Mono<Collection> saveCollection() throws DescriptionException, NameException {
    Collection collection = Collection.createCollection(
        UUID.randomUUID(),
        new Name("Damiano"),
        new Description("asad")
    );
    return elementRepository.save(collection);
  }
}
