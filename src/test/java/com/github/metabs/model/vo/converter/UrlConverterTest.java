package com.github.metabs.model.vo.converter;

import com.github.metabs.model.ObjectMother;
import java.net.URL;
import org.junit.Assert;
import org.junit.Test;
import org.neo4j.driver.Value;

public class UrlConverterTest {
  @Test
  public void shouldConverterDescriptionInValueAndValueInName() throws Exception {
    UrlConverter urlConverter = new UrlConverter();
    URL link = ObjectMother.generateRandomLink();
    Value value = urlConverter.write(link);
    Assert.assertEquals(link.toString(), value.asString());
    URL link2 = urlConverter.read(value);
    Assert.assertEquals(link, link2);
  }
}
