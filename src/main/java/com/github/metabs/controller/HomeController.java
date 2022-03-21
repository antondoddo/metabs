package com.github.metabs.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

  @Autowired
  Neo4jClient neo4jClient;

  @GetMapping("/")
  public ResponseEntity<Map<String, String>> healthCheck() {
    HashMap<String, String> map = new HashMap<>();
    map.put("name", "metabs");

    String check = "healthy";
    HttpStatus status = HttpStatus.OK;

    try {
      Optional<Integer> neo4jResult = neo4jClient.query("RETURN 1").fetchAs(Integer.class).one();
      if (neo4jResult.isEmpty() || neo4jResult.get() != 1) {
        check = "not healthy";
        status = HttpStatus.INTERNAL_SERVER_ERROR;
      }
    } catch (Exception e) {
      check = "not healthy";
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    map.put("neo4j", check);


    return new ResponseEntity<>(map, status);
  }
}