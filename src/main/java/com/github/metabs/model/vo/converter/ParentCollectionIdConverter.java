package com.github.metabs.model.vo.converter;

import com.github.metabs.model.vo.ParentCollectionId;
import java.util.UUID;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

public class ParentCollectionIdConverter implements
    Neo4jPersistentPropertyConverter<ParentCollectionId> {
  @Override
  public Value write(ParentCollectionId source) {
    if (source != null) {
      return new StringValue(source.getValue().toString());
    }
    return null;
  }

  @Override
  public ParentCollectionId read(Value source) {
    if (!source.isNull()) {
      return new ParentCollectionId(UUID.fromString(source.asString()));
    }
    return null;
  }
}
