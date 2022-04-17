package com.github.metabs.model;

import org.junit.Assert;
import org.junit.Test;

public class AccessTest {

  @Test
  public void shouldCreateAccess() throws Exception {
    User user = ObjectMother.generateRandomUser();
    Access access = new Access(Role.ADMIN, user);
    Assert.assertSame(access.getUser(), user);
    Assert.assertEquals(access.getRole(), Role.ADMIN);
  }
}
