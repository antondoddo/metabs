package com.github.metabs.model;

import com.github.javafaker.Faker;
import com.github.metabs.model.exception.DescriptionException;
import com.github.metabs.model.exception.NameException;
import com.github.metabs.model.exception.PasswordException;
import com.github.metabs.model.exception.UsernameException;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import com.github.metabs.model.vo.Password;
import com.github.metabs.model.vo.Username;
import com.github.metabs.service.SaveElementDto;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ObjectMother {

  private static final Faker faker = new Faker();

  public static Name generateRandomName() throws NameException {
    return new Name(faker.superhero().name());
  }

  public static Username generateRandomUsername() throws UsernameException {
    return new Username(faker.animal().name());
  }

  public static Password generateRandomPassword() throws PasswordException {
    return new Password(faker.animal().name());
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

  public static Role generateRandomRole() {
    List<Role> values =
        Collections.unmodifiableList(Arrays.asList(Role.values()));
    Random random = new Random();
    return values.get(random.nextInt(values.size()));
  }

  public static User generateRandomUser() throws NameException, UsernameException, PasswordException {
    return User.createUser(
        UUID.randomUUID(),
        ObjectMother.generateRandomName(),
        ObjectMother.generateRandomName(),
        ObjectMother.generateRandomUsername(),
        ObjectMother.generateRandomPassword()
    );
  }

  public static Access generateRandomAccess() throws NameException, UsernameException, PasswordException {
    return new Access(
        ObjectMother.generateRandomRole(),
        ObjectMother.generateRandomUser()
    );
  }


  public static Access generateAdminAccess() throws NameException, UsernameException, PasswordException {
    return new Access(
        Role.ADMIN,
        ObjectMother.generateRandomUser()
    );
  }

  public static Tab generateRandomTab()
      throws NameException,
      MalformedURLException,
      DescriptionException, UsernameException, PasswordException {

    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    URL link = ObjectMother.generateRandomLink();
    Description description = ObjectMother.generateRandomDescription();
    Access creator = ObjectMother.generateRandomAccess();
    return Tab.createTab(id, name, link, description, creator);
  }

  public static Tab generateRandomTabWithParent() throws
      NameException,
      MalformedURLException,
      DescriptionException, UsernameException, PasswordException {
    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    URL link = ObjectMother.generateRandomLink();
    Description description = ObjectMother.generateRandomDescription();

    Collection collectionParent = ObjectMother.generateRandomCollection();

    return Tab.createTabWithParent(id, collectionParent, name, link, description);
  }

  public static Collection generateRandomCollection() throws NameException,
      DescriptionException, UsernameException, PasswordException {

    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    Description description = ObjectMother.generateRandomDescription();
    Access access = ObjectMother.generateRandomAccess();
    return Collection.createCollection(id, name, description, access);
  }


  public static Collection generateRandomCollection(Access access) throws NameException,
      DescriptionException {

    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    Description description = ObjectMother.generateRandomDescription();
    return Collection.createCollection(id, name, description, access);
  }

  public static SaveElementDto generateSaveElementRequestDto() throws
      NameException,
      MalformedURLException,
      DescriptionException {

    Name name = ObjectMother.generateRandomName();
    URL link = ObjectMother.generateRandomLink();
    Description description = ObjectMother.generateRandomDescription();
    SaveElementDto saveElementDto = new SaveElementDto();
    saveElementDto.setName(name);
    saveElementDto.setLink(link);
    saveElementDto.setDescription(description);

    return saveElementDto;

  }

  public static Collection generateRandomCollectionWithParent() throws
      NameException,
      DescriptionException, UsernameException, PasswordException {

    Collection collectionParent = ObjectMother.generateRandomCollection();

    UUID id = UUID.randomUUID();
    Name name = ObjectMother.generateRandomName();
    Description description = ObjectMother.generateRandomDescription();

    return Collection.createCollectionWithParent(id, collectionParent, name, description);
  }
}
