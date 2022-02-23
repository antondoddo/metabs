package com.github.metabs.model.vo;

import com.github.metabs.model.exception.DescriptionException;

public class Description {

  private final String value;

  public Description(String value) {
    if (value == null || value.isEmpty()) {
      throw DescriptionException.empty();
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
    Description objCasted = (Description) obj;
    return this.value.equals(objCasted.value);
  }
}
