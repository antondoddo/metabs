package com.github.metabs.model.vo;

import com.github.metabs.model.exception.PasswordException;

public class Password {

  private final String value;

  public Password(String value) throws PasswordException {
    if (value == null || value.isEmpty()) {
      throw PasswordException.passwordEmpty();
    }
    if (value.length() > 81) {
      throw PasswordException.passwordNotValid();
    }
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    Password objCasted = (Password) obj;
    return this.value.equals(objCasted.value);
  }
}
