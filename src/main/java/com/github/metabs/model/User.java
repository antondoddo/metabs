package com.github.metabs.model;

import com.github.metabs.model.vo.Name;
import com.github.metabs.model.vo.Password;
import com.github.metabs.model.vo.Username;
import com.github.metabs.model.vo.converter.NameConverter;
import com.github.metabs.model.vo.converter.PasswordConverter;
import com.github.metabs.model.vo.converter.UsernameConverter;
import java.util.UUID;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Node("User")
public class User {

  @Id
  private UUID id;
  @ConvertWith(converter = NameConverter.class)
  private Name firstname;
  @ConvertWith(converter = NameConverter.class)
  private Name lastname;
  @ConvertWith(converter = UsernameConverter.class)
  private Username username;
  @ConvertWith(converter = PasswordConverter.class)
  private Password password;

  private User() {
  }

  private User(UUID id, Name firstname, Name lastname, Username username, Password password) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.password = password;
  }

  public static User createUser(UUID id,
                                Name firstname,
                                Name lastname,
                                Username username,
                                Password password
  ) {
    return new User(id, firstname, lastname, username, password);
  }

  public static User createUserWithoutArgs() {
    return new User();
  }

  public UUID getId() {
    return id;
  }

  public void changeId(UUID id) {
    this.id = id;
  }

  public Name getFirstname() {
    return firstname;
  }

  public void changeFirstName(Name firstname) {
    this.firstname = firstname;
  }

  public Name getLastname() {
    return lastname;
  }

  public void changeLastName(Name lastname) {
    this.lastname = lastname;
  }

  public Username getUsername() {
    return username;
  }

  public void changeUsername(Username username) {
    this.username = username;
  }

  public Password getPassword() {
    return password;
  }

  public void changePassword(Password password) {
    this.password = password;
  }
}
