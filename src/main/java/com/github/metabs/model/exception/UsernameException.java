package com.github.metabs.model.exception;

public class UsernameException extends Exception {

  private UsernameException(String message) {
    super(message);
  }

  public static UsernameException usernameEmpty() {
    return new UsernameException("L'username non può essere vuoto!");
  }

  public static UsernameException usernameTooLong() {
    return new UsernameException("Inserire un nome con meno di 80 caratteri!");
  }
}
