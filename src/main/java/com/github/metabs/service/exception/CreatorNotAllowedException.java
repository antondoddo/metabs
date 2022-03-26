package com.github.metabs.service.exception;

public class CreatorNotAllowedException extends Exception {

  private CreatorNotAllowedException(String message) {
    super(message);
  }

  public static CreatorNotAllowedException notAllowed() {
    return new CreatorNotAllowedException("Il creator non è abilitato per questa risorsa!");
  }
}
