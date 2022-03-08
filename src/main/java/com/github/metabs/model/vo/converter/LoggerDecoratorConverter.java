package com.github.metabs.model.vo.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LoggerDecoratorConverter<X, Y> implements AttributeConverter<X, Y> {

  private final AttributeConverter<X, Y> baseAttributeConverter;

  public LoggerDecoratorConverter(AttributeConverter<X, Y> baseAttributeConverter) {

    this.baseAttributeConverter = baseAttributeConverter;
  }

  @Override
  public Y convertToDatabaseColumn(X x) {
    try {
      return this.baseAttributeConverter.convertToDatabaseColumn(x);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }

  @Override
  public X convertToEntityAttribute(Y y) {
    try {
      return this.baseAttributeConverter.convertToEntityAttribute(y);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }
}
