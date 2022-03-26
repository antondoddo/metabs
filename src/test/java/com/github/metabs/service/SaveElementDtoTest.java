package com.github.metabs.service;

import com.github.metabs.model.Access;
import com.github.metabs.model.ObjectMother;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.net.URL;
import org.junit.Assert;
import org.junit.Test;

public class SaveElementDtoTest {

  SaveElementDto saveElementDto = new SaveElementDto();

  @Test
  public void shouldRename() throws Exception {
    Name name = ObjectMother.generateRandomName();
    saveElementDto.setName(name);
    Assert.assertEquals(name, saveElementDto.getName());
  }

  @Test
  public void shouldChangeDescription() throws Exception {
    Description description = ObjectMother.generateRandomDescription();
    saveElementDto.setDescription(description);
    Assert.assertEquals(description, saveElementDto.getDescription());
  }

  @Test
  public void shouldChangeLink() throws Exception {
    URL link = ObjectMother.generateRandomLink();
    saveElementDto.setLink(link);
    Assert.assertEquals(link, saveElementDto.getLink());
  }

  @Test
  public void shouldChangeCreator() throws Exception {
    Access creator = ObjectMother.generateRandomAccess();
    saveElementDto.setCreator(creator);
    Assert.assertEquals(creator, saveElementDto.getCreator());
  }
}
