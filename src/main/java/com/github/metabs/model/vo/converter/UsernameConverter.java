package com.github.metabs.model.vo.converter;

import com.github.metabs.model.exception.UsernameException;
import com.github.metabs.model.vo.Username;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

public class UsernameConverter implements Neo4jPersistentPropertyConverter<Username> {

  @Override
  public Value write(Username source) {
    return new StringValue(source.getValue());
  }

  @Override
  public Username read(Value source) {
    try {
      return new Username(source.asString());
    } catch (UsernameException e) {
      throw new RuntimeException(e);
    }
  }
}
