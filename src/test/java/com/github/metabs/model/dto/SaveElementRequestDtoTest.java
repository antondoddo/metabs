package com.github.metabs.model.dto;

import com.github.metabs.model.ObjectMother;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.net.URL;
import org.junit.Assert;
import org.junit.Test;

public class SaveElementRequestDtoTest {

  SaveElementRequestDto saveElementRequestDto = new SaveElementRequestDto();

  @Test
  public void shouldRename() throws Exception {
    Name name = ObjectMother.generateRandomName();
    saveElementRequestDto.rename(name);
    Assert.assertEquals(name, saveElementRequestDto.getName());
  }

  @Test
  public void shouldChangeDescription() throws Exception {
    Description description = ObjectMother.generateRandomDescription();
    saveElementRequestDto.changeDescription(description);
    Assert.assertEquals(description, saveElementRequestDto.getDescription());
  }

  @Test
  public void shouldChangeLink() throws Exception {
    URL link = ObjectMother.generateRandomLink();
    saveElementRequestDto.changeLink(link);
    Assert.assertEquals(link, saveElementRequestDto.getLink());
  }
}
