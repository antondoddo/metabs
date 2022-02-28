package com.github.metabs.repository;

import com.github.metabs.model.Collection;
import com.github.metabs.model.Element;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

@Repository
@Transactional("neo4jTransactionManager")
public interface ElementRepository extends ReactiveNeo4jRepository<Collection, UUID> {
  Mono<Collection> findById(UUID id);
  Mono<Collection> save(Collection element);
}
