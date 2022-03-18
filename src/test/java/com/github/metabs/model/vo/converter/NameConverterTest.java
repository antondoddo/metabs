package com.github.metabs.model.vo.converter;

import com.github.metabs.model.ObjectMother;
import com.github.metabs.model.vo.Name;
import org.junit.Assert;
import org.junit.Test;
import org.neo4j.driver.Value;

public class NameConverterTest {

  @Test
  public void shouldConverterNameInValueAndValueInName() throws Exception {
    NameConverter nameConverter = new NameConverter();
    Name name = ObjectMother.generateRandomName();
    Value value = nameConverter.write(name);
    Assert.assertEquals(name.getValue(), value.asString());
    Name name2 = nameConverter.read(value);
    Assert.assertEquals(name, name2);
  }
}
