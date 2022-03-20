package com.github.metabs.model;

import com.github.javafaker.Faker;
import com.github.metabs.model.dto.SaveElementDto;
import com.github.metabs.model.exception.DescriptionException;
import com.github.metabs.model.exception.NameException;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class ObjectMother {

  private static final Faker faker = new Faker();

  public static Name generateRandomName() throws NameException {
    return new Name(faker.superhero().name());
  }

  public static URL generateRandomLink() throws MalformedURLException {
    return new URL("https://" + faker.internet().url());
  }

  public static Description generateRandomDescription() throws DescriptionException {
    return new Description(faker.music().instrument());
  }

  public static Description generateEmptyDescription() throws DescriptionException {
    return new Description("");
  }

  public static Tab generateRandomTab()
      throws NameException,
      MalformedURLException,
      DescriptionException {

    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    URL link = ObjectMother.generateRandomLink();
    Description description = ObjectMother.generateRandomDescription();

    return Tab.createTab(id, name, link, description);
  }

  public static Tab generateRandomTabWithParent() throws
      NameException,
      MalformedURLException,
      DescriptionException {
    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    URL link = ObjectMother.generateRandomLink();
    Description description = ObjectMother.generateRandomDescription();

    UUID idParent = UUID.randomUUID();
    Name nameParent = ObjectMother.generateRandomName();
    Description descriptionParent = ObjectMother.generateRandomDescription();
    Collection collectionParent = Collection.createCollection(
        idParent, nameParent, descriptionParent
    );

    return Tab.createTabWithParent(id, collectionParent, name, link, description);
  }

  public static Collection generateRandomCollection() throws NameException,
      DescriptionException {

    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    Description description = ObjectMother.generateRandomDescription();

    return Collection.createCollection(id, name, description);
  }

  public static SaveElementDto generateSaveElementRequestDto() throws
      NameException,
      MalformedURLException,
      DescriptionException {

    Name name = ObjectMother.generateRandomName();
    URL link = ObjectMother.generateRandomLink();
    Description description = ObjectMother.generateRandomDescription();
    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.rename(name);
    saveElementDto.changeLink(link);
    saveElementDto.changeDescription(description);

    return saveElementDto;

  }

  public static Collection generateRandomCollectionWithParent() throws
      NameException,
      DescriptionException {

    UUID idParent = UUID.randomUUID();
    Name nameParent = ObjectMother.generateRandomName();
    Description descriptionParent = ObjectMother.generateRandomDescription();
    Collection collectionParent = Collection.createCollection(
        idParent, nameParent, descriptionParent
    );

    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    Description description = ObjectMother.generateRandomDescription();

    return Collection.createCollectionWithParent(id, collectionParent, name, description);
  }

}
