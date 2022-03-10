package com.github.metabs.repository;

import com.github.metabs.model.Element;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface ElementRepository extends Neo4jRepository<Element, UUID> {
}
