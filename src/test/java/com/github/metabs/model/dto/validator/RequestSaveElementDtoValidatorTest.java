package com.github.metabs.model.dto.validator;

import com.github.metabs.model.dto.SaveElementRequest;
import com.github.metabs.model.dto.SaveElementDto;
import org.junit.Assert;
import org.junit.Test;

public class RequestSaveElementDtoValidatorTest {

  @Test
  public void shouldCreateRequestElementValidator() {

    SaveElementDto saveElementDto = new SaveElementDto();
    RequestElementValidator requestElementValidator = new RequestElementValidator(
        saveElementDto
    );
    Assert.assertSame(
        requestElementValidator.getSaveElementDto(),
        saveElementDto
    );
  }

  @Test
  public void shouldSupportsSameClass() {
    SaveElementDto saveElementDto = new SaveElementDto();
    RequestElementValidator requestElementValidator = new RequestElementValidator(
        saveElementDto
    );
    SaveElementRequest saveElementRequest = new SaveElementRequest(
        "nome",
        "descrizione",
        "http://www.google.com");
    Assert.assertTrue(requestElementValidator.supports(saveElementRequest.getClass()));
  }

  @Test
  public void shouldValidate() {
    SaveElementDto saveElementDto = new SaveElementDto();
    RequestElementValidator requestElementValidator = new RequestElementValidator(
        saveElementDto
    );
    SaveElementRequest saveElementRequest = new SaveElementRequest(
        "nome",
        "descrizione",
        "http://www.google.com");
    requestElementValidator.validate(saveElementRequest, null);
    Assert.assertEquals(saveElementDto.getName().getValue(), "nome");
    Assert.assertEquals(saveElementDto.getDescription().getValue(), "descrizione");
    Assert.assertEquals(saveElementDto.getLink().toString(), "http://www.google.com");
  }
}
