package com.github.metabs.model.vo;

import com.github.metabs.model.exception.UsernameException;

public class Username {

  private final String value;

  public Username(String value) throws UsernameException {
    if (value == null || value.isEmpty()) {
      throw UsernameException.usernameEmpty();
    }
    if (value.length() > 41) {
      throw UsernameException.usernameTooLong();
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
    Username objCasted = (Username) obj;
    return this.value.equals(objCasted.value);
  }
}
