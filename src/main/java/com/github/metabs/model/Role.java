package com.github.metabs.model;

public enum Role {
  ADMIN,
  EDITOR,
  VIEWER;

  public boolean hasEditorPermission() {
    return this.equals(Role.ADMIN) || this.equals(Role.EDITOR);
  }
}
