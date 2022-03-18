package com.github.metabs.model.dto.validator;

import com.github.metabs.model.dto.ElementDto;
import com.github.metabs.model.dto.SaveElementRequestDto;
import org.junit.Assert;
import org.junit.Test;

public class RequestElementValidatorTest {

  @Test
  public void shouldCreateRequestElementValidator() {

    SaveElementRequestDto saveElementRequestDto = new SaveElementRequestDto();
    RequestElementValidator requestElementValidator = new RequestElementValidator(
        saveElementRequestDto
    );
    Assert.assertSame(
        requestElementValidator.getSaveElementRequestDto(),
        saveElementRequestDto
    );
  }

  @Test
  public void shouldSupportsSameClass() {
    SaveElementRequestDto saveElementRequestDto = new SaveElementRequestDto();
    RequestElementValidator requestElementValidator = new RequestElementValidator(
        saveElementRequestDto
    );
    ElementDto elementDto = new ElementDto(
        "nome",
        "descrizione",
        "http://www.google.com");
    Assert.assertTrue(requestElementValidator.supports(elementDto.getClass()));
  }

  @Test
  public void shouldValidate() {
    SaveElementRequestDto saveElementRequestDto = new SaveElementRequestDto();
    RequestElementValidator requestElementValidator = new RequestElementValidator(
        saveElementRequestDto
    );
    ElementDto elementDto = new ElementDto(
        "nome",
        "descrizione",
        "http://www.google.com");
    requestElementValidator.validate(elementDto, null);
    Assert.assertEquals(saveElementRequestDto.getName().getValue(), "nome");
    Assert.assertEquals(saveElementRequestDto.getDescription().getValue(), "descrizione");
    Assert.assertEquals(saveElementRequestDto.getLink().toString(), "http://www.google.com");
  }
}
