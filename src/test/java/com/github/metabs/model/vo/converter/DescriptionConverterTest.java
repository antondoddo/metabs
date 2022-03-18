package com.github.metabs.model.vo.converter;

import com.github.metabs.model.ObjectMother;
import com.github.metabs.model.vo.Description;
import org.junit.Assert;
import org.junit.Test;
import org.neo4j.driver.Value;

public class DescriptionConverterTest {
  @Test
  public void shouldConverterDescriptionInValueAndValueInName() throws Exception {
    DescriptionConverter descriptionConverter = new DescriptionConverter();
    Description description = ObjectMother.generateRandomDescription();
    Value value = descriptionConverter.write(description);
    Assert.assertEquals(description.getValue(), value.asString());
    Description description2 = descriptionConverter.read(value);
    Assert.assertEquals(description, description2);
  }
}
