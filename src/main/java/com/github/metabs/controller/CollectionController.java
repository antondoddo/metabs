package com.github.metabs.controller;

import com.github.metabs.controller.dto.SaveElementRequest;
import com.github.metabs.controller.validator.SaveElementRequestValidator;
import com.github.metabs.model.Element;
import com.github.metabs.service.ElementService;
import com.github.metabs.service.dto.SaveElementDto;
import com.github.metabs.service.exception.ParentNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
  public Object saveElement(
          @RequestBody SaveElementRequest saveElementRequest,
          SaveElementRequestValidator saveElementRequestValidator,
          BindingResult result
  ) {
    saveElementRequestValidator.validate(saveElementRequest, result);
    if (result.hasErrors()) {
      return new ResponseEntity<>(result.getAllErrors(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    SaveElementDto dto = new SaveElementDto(
            saveElementRequestValidator.getName(),
            saveElementRequestValidator.getDescription(),
            saveElementRequestValidator.getLink()
    );

    try {
      return new ResponseEntity<>(
              elementService.saveElement(dto),
              HttpStatus.CREATED
      );
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @PostMapping("/{id}")
  @ResponseBody
  public Object saveElementWithParent(
          @RequestBody SaveElementRequest saveElementRequest,
          @PathVariable("id") UUID parentCollectionId,
          SaveElementRequestValidator saveElementRequestValidator,
          BindingResult result
  ) {

    saveElementRequestValidator.validate(saveElementRequest, result);
    if (result.hasErrors()) {
      return new ResponseEntity<>(result.getAllErrors(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    SaveElementDto dto = new SaveElementDto(
            saveElementRequestValidator.getName(),
            saveElementRequestValidator.getDescription(),
            saveElementRequestValidator.getLink()
    );

    try {
      return new ResponseEntity<>(
              elementService.saveElementWithParent(parentCollectionId, dto),
              HttpStatus.CREATED
      );
    } catch (ParentNotFoundException ex) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}