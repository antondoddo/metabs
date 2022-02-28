package com.github.metabs.model.exception;

public class DescriptionException extends Exception {

  private DescriptionException(String message) {
    super(message);
  }

  public static DescriptionException empty() {
    return new DescriptionException("Inserire una descrizione!");
  }
}
