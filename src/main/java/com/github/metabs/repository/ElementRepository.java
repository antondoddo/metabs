package com.github.metabs.repository;

import com.github.metabs.model.Element;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementRepository extends Neo4jRepository<Element, UUID> {

  @Query(
      "MATCH p=(child:Element{id:$id})-[:HAS_PARENT*0..1]->(parent:Element) "
          + "RETURN child, collect(relationships(p)), collect(nodes(p))"
  )
  Optional<Element> findById(UUID id);

  @Query(
      "MATCH (n:Element {id:$id})" +
          "DELETE n"
  )
  void deleteById(UUID id);
}

