package com.github.metabs.repository;

import com.github.metabs.model.User;
import java.util.UUID;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<User, UUID> {

  User findByUsername(String username);
}
