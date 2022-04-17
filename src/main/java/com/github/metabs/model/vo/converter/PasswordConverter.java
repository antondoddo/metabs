package com.github.metabs.model.vo.converter;

import com.github.metabs.model.exception.PasswordException;
import com.github.metabs.model.vo.Password;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

public class PasswordConverter implements Neo4jPersistentPropertyConverter<Password> {

  @Override
  public Value write(Password source) {
    return new StringValue(source.getValue());
  }

  @Override
  public Password read(Value source) {
    try {
      return new Password(source.asString());
    } catch (PasswordException e) {
      throw new RuntimeException(e);
    }
  }
}
