package com.github.metabs.model;

import com.github.javafaker.Faker;
import com.github.metabs.model.exception.DescriptionException;
import com.github.metabs.model.exception.NameException;
import com.github.metabs.model.vo.Description;
import com.github.metabs.model.vo.Name;
import java.net.MalformedURLException;
import java.net.URL;

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

}
