package com.blog.BlogWeb.exception;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

  private final String resourceName;
  private final String fieldName;
  private final Integer fieldValue;

  public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
    super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }
}
