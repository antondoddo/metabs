package com.github.metabs.service.exception;

public class ParentNotFoundException extends Exception {

  private ParentNotFoundException(String message) {
    super(message);
  }

  public static ParentNotFoundException parentNotFound() {
    return new ParentNotFoundException("Padre dell'elemento non trovato!");
  }
}
