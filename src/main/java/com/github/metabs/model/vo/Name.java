package com.github.metabs.model.vo;

import com.github.metabs.model.exception.NameException;

public class Name {
  private final String value;


  public Name(String value) throws NameException {
    if (value == null || value.isEmpty()) {
      throw NameException.empty();
    }
    if (value.length() > 81) {
      throw NameException.tooLong();
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
    Name objCasted = (Name) obj;
    return this.value.equals(objCasted.value);
  }
}
