package com.github.metabs.model.vo;

import com.github.metabs.model.exception.DescriptionException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class DescriptionTest {

  @Test
  public void shouldReturnValue() throws DescriptionException {
    Description description = new Description("Questa è una collection!");
    Assert.assertEquals(description.getValue(), "Questa è una collection!");
  }

  @Test
  public void shouldEmptyThrowDescriptionException() {
    DescriptionException exception = Assertions.assertThrows(DescriptionException.class, () -> {
      Description description = new Description("");
    });
    Assert.assertEquals("Inserire una descrizione!", exception.getMessage());
  }

  @Test
  public void shouldBeEquals() throws DescriptionException {
    Description description1 = new Description("Descrizione");
    Description description2 = new Description("Descrizione");
    Assert.assertEquals(description1, description2);
  }
}
