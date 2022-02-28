package com.github.metabs.model.exception;

public class NameException extends Exception {

  private NameException(String message) {
    super(message);
  }

  public static NameException empty() {
    return new NameException("Inserire un nome che non sia vuoto!");
  }

  public static NameException tooLong() {
    return new NameException("Inserire un nome con meno di 80 caratteri!");
  }
}
