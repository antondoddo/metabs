package com.github.metabs.model.vo.converter;

import java.net.MalformedURLException;
import java.net.URL;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;


public class UrlConverter implements Neo4jPersistentPropertyConverter<URL> {


  @Override
  public Value write(URL source) {
    return new StringValue(source.toString());
  }

  @Override
  public URL read(Value source) {
    try {
      return new URL(source.asString());
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
