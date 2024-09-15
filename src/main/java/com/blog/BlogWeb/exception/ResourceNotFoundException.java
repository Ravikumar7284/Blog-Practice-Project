package com.blog.BlogWeb.exception;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

  private final String resourceName;
  private final String fieldName;
  private Integer fieldValue;
  private String textFieldValue;

  public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
    super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }

  public ResourceNotFoundException(String resourceName, String fieldName, String textFieldValue) {
    super(String.format("%s not found with %s : %s", resourceName, fieldName, textFieldValue));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.textFieldValue = textFieldValue;
  }
}
