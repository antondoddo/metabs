package com.github.metabs.model.exception;

public class PasswordException extends Exception {

  private PasswordException(String message) {
    super(message);
  }

  public static PasswordException passwordEmpty() {
    return new PasswordException("La password non può essere vuota!");
  }

  public static PasswordException passwordNotValid() {
    return new PasswordException("Password non valida!");
  }
}
