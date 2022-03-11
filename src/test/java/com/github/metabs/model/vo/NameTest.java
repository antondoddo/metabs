package com.github.metabs.model.vo;

import com.github.metabs.model.exception.NameException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class NameTest {

  @Test
  public void shouldReturnValue() throws NameException {
    Name name = new Name("Personal");
    Assert.assertSame(name.getValue(), "Personal");
  }

  @Test
  public void shouldThrowEmptyNameException() {
    NameException exception = Assertions.assertThrows(NameException.class, () -> {
      Name name = new Name("");
    });
    Assert.assertEquals("Inserire un nome che non sia vuoto!", exception.getMessage());
  }

  @Test
  public void shouldThrowTooLongNameException() {
    NameException exception = Assertions.assertThrows(NameException.class, () -> {
      Name name = new Name(""
              + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
              + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    });
    Assert.assertEquals("Inserire un nome con meno di 80 caratteri!", exception.getMessage());
  }

  @Test
  public void shouldBeEquals() throws NameException {
    Name name1 = new Name("Personal");
    Name name2 = new Name("Personal");
    Assert.assertEquals(name1, name2);
  }
}
