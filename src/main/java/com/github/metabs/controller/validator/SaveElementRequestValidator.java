package com.github.metabs.controller.validator;

import com.github.metabs.controller.dto.SaveElementRequest;
import com.github.metabs.model.exception.DescriptionException;
import com.github.metabs.model.exception.NameException;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SaveElementRequestValidator implements Validator {

  private Name name;
  private Description description;
  private Optional<URL> link;

  @Override
  public boolean supports(Class<?> clazz) {
    return SaveElementRequest.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    SaveElementRequest dto = (SaveElementRequest) target;

    try {
      this.name = new Name(dto.getName());
    } catch (NameException e) {
      errors.reject("name", e.getMessage());
    }


    try {
      this.description = new Description(dto.getDescription());
    } catch (DescriptionException e) {
      errors.reject("description", e.getMessage());
    }


    if (dto.getLink() == null || dto.getLink().isEmpty()) {
      this.link = Optional.empty();
      return;
    }

    try {
      this.link = Optional.of(new URL(dto.getLink()));
    } catch (MalformedURLException e) {
      errors.reject("link", e.getMessage());
    }
  }

  public Name getName() {
    return name;
  }

  public Description getDescription() {
    return description;
  }

  public Optional<URL> getLink() {
    return link;
  }
}