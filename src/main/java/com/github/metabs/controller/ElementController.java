package com.github.metabs.controller;

import com.github.metabs.model.Element;
import com.github.metabs.model.dto.ElementDto;
import com.github.metabs.model.dto.SaveElementRequestDto;
import com.github.metabs.model.dto.validator.RequestElementValidator;
import com.github.metabs.service.ElementService;
import com.github.metabs.service.exception.ParentNotFoundException;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("elements")
public class ElementController {

  @Autowired
  ElementService elementService;

  @Resource(name = "requestElementValidator")
  RequestElementValidator validator;

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
  public ResponseEntity<Object> saveElement(@RequestBody ElementDto elementDto,
                                            BindingResult result) {

    validator.validate(elementDto, result);
    SaveElementRequestDto saveElementRequestDto = validator.getSaveElementRequestDto();
    if (result.hasErrors()) {
      return new ResponseEntity<>(result.getAllErrors(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    try {
      return new ResponseEntity<>(
          elementService.saveElement(saveElementRequestDto),
          HttpStatus.CREATED
      );
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @PostMapping("/{id}")
  @ResponseBody
  public ResponseEntity<Object> saveElementWithParent(
      @RequestBody ElementDto elementDto,
      @PathVariable("id") UUID parentCollectionId,
      BindingResult result
  ) {
    validator.validate(elementDto, result);
    SaveElementRequestDto saveElementRequestDto = validator.getSaveElementRequestDto();
    if (result.hasErrors()) {
      return new ResponseEntity<>(result.getAllErrors(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    try {
      return new ResponseEntity<>(
          elementService.saveElementWithParent(saveElementRequestDto, parentCollectionId),
          HttpStatus.CREATED);
    } catch (ParentNotFoundException ex) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteElementById(
      @PathVariable("id") UUID id) {
    try {
      Optional<Element> element = elementService.getElementById(id);
      if (!element.isPresent()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      elementService.deleteElementById(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}