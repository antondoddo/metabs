package com.github.metabs.controller;

import com.github.metabs.model.Element;
import com.github.metabs.model.dto.ElementDto;
import com.github.metabs.model.exception.DescriptionException;
import com.github.metabs.model.exception.NameException;
import com.github.metabs.service.ElementService;
import com.github.metabs.service.exception.ParentNotFoundException;
import java.net.MalformedURLException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  ElementService elementService;

  @GetMapping("/{id}")
  public ResponseEntity<Element> getElementById(@PathVariable UUID id) {
    try {
      Optional<Element> element = elementService.getElementById(id);
      if (!element.isPresent()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      Element trueElement = element.get();
      return new ResponseEntity<>(trueElement, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/")
  @ResponseBody
  public ResponseEntity<Element> saveElement(@RequestBody ElementDto elementDto) {
    try {
      return new ResponseEntity<>(
          elementService.saveElement(elementDto),
          HttpStatus.CREATED
      );
    } catch (DescriptionException | NameException | MalformedURLException ex) {
      return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @PostMapping("/{id}")
  @ResponseBody
  public ResponseEntity<Element> saveElementWithParent(
      @RequestBody ElementDto elementDto,
      @PathVariable("id") UUID parentCollectionId
  ) {
    try {
      return new ResponseEntity<>(
          elementService.saveElementWithParent(elementDto, parentCollectionId),
          HttpStatus.CREATED
      );
    } catch (DescriptionException | NameException | MalformedURLException ex) {
      return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (ParentNotFoundException ex) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}