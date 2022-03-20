package com.github.metabs.model.dto.validator;

import com.github.metabs.model.dto.SaveElementDto;
import com.github.metabs.model.dto.SaveElementRequest;
import com.github.metabs.model.exception.DescriptionException;
import com.github.metabs.model.exception.NameException;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//@Component
public class RequestElementValidator implements Validator {


  private final SaveElementDto saveElementDto;

  public RequestElementValidator(SaveElementDto saveElementDto) {
    this.saveElementDto = saveElementDto;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return SaveElementRequest.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {

    SaveElementRequest saveElementRequest = (SaveElementRequest) target;
    try {
      saveElementDto.rename(new Name(saveElementRequest.getName()));
    } catch (NameException ex) {
      errors.reject("name", ex.getMessage());
    }
    try {
      saveElementDto.changeDescription(new Description(saveElementRequest.getDescription()));
    } catch (DescriptionException ex) {
      errors.reject("description", ex.getMessage());
    }
    if (saveElementRequest.getLink() == null || saveElementRequest.getLink().isEmpty()) {
      return;
    }
    try {
      saveElementDto.changeLink(new URL(saveElementRequest.getLink()));
    } catch (MalformedURLException ex) {
      errors.reject("link", ex.getMessage());
    }
  }

  public SaveElementDto getSaveElementDto() {
    return saveElementDto;
  }
}
