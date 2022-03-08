package com.github.metabs.repository;

import com.github.metabs.model.Element;
import java.util.UUID;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ElementRepository extends Neo4jRepository<Element, UUID> {
}
