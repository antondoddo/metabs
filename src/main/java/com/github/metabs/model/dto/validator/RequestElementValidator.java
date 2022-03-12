package com.github.metabs.model.dto.validator;

import com.github.metabs.model.dto.ElementDto;
import com.github.metabs.model.dto.SaveElementRequestDto;
import com.github.metabs.model.exception.DescriptionException;
import com.github.metabs.model.exception.NameException;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RequestElementValidator implements Validator {


  private final SaveElementRequestDto saveElementRequestDto;

  public RequestElementValidator(SaveElementRequestDto saveElementRequestDto) {
    this.saveElementRequestDto = saveElementRequestDto;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return ElementDto.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {

    ElementDto elementDto = (ElementDto) target;
    try {
      saveElementRequestDto.setName(new Name(elementDto.getName()));
    } catch (NameException ex) {
      errors.reject("name", ex.getMessage());
    }
    try {
      saveElementRequestDto.setDescription(new Description(elementDto.getDescription()));
    } catch (DescriptionException ex) {
      errors.reject("description", ex.getMessage());
    }
    if (elementDto.getLink() == null || elementDto.getLink().isEmpty()) {
      return;
    }
    try {
      saveElementRequestDto.setLink(new URL(elementDto.getLink()));
    } catch (MalformedURLException ex) {
      errors.reject("link", ex.getMessage());
    }
  }

  public SaveElementRequestDto getSaveElementRequestDto() {
    return saveElementRequestDto;
  }
}
