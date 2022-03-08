package com.github.metabs.model.vo.converter;

import com.github.metabs.model.vo.Description;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;


public class DescriptionConverter implements Neo4jPersistentPropertyConverter<Description> {

  @Override
  public Value write(Description source) {
    return new StringValue(source.getValue());
  }

  @Override
  public Description read(Value source) {
    try {
      return new Description(source.asString());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
