package com.github.metabs.model.vo.converter;

import com.github.metabs.model.exception.NameException;
import com.github.metabs.model.vo.Name;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

public class NameConverter implements Neo4jPersistentPropertyConverter<Name> {

  @Override
  public Value write(Name source) {
    return new StringValue(source.getValue());
  }

  @Override
  public Name read(Value source) {
    try {
      return new Name(source.asString());
    } catch (NameException e) {
      throw new RuntimeException(e);
    }
  }
}
