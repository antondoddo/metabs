package com.github.metabs.repository;

import com.github.metabs.model.Collection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface CollectionRepository extends Neo4jRepository<Collection, UUID> {

  @Query(value = "MATCH (c:Collection) WHERE c.id = $id return c")
  public Optional<Collection> findCollectionWithParent(UUID id);

}
