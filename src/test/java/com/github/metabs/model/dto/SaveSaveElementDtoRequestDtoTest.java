package com.github.metabs.model.dto;

import com.github.metabs.model.ObjectMother;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.net.URL;
import org.junit.Assert;
import org.junit.Test;

public class SaveSaveElementDtoRequestDtoTest {

  SaveElementDto saveElementDto = new SaveElementDto();

  @Test
  public void shouldRename() throws Exception {
    Name name = ObjectMother.generateRandomName();
    saveElementDto.rename(name);
    Assert.assertEquals(name, saveElementDto.getName());
  }

  @Test
  public void shouldChangeDescription() throws Exception {
    Description description = ObjectMother.generateRandomDescription();
    saveElementDto.changeDescription(description);
    Assert.assertEquals(description, saveElementDto.getDescription());
  }

  @Test
  public void shouldChangeLink() throws Exception {
    URL link = ObjectMother.generateRandomLink();
    saveElementDto.changeLink(link);
    Assert.assertEquals(link, saveElementDto.getLink());
  }
}
