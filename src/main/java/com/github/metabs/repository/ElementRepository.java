package com.github.metabs.repository;

import com.github.metabs.model.Element;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface ElementRepository extends Neo4jRepository<Element, UUID> {

  @Query(value = "MATCH (e:Element)-[:HAS_PARENT]->(c:Collection) WHERE e.id = $id return e, c as parentCollection")
  public Optional<Element> findElementWithParent(UUID id);
}
