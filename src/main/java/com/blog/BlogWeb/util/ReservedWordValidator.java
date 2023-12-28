package com.blog.BlogWeb.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReservedWordValidator implements ConstraintValidator<ReservedWord, String> {

  private String reservedWord;

  @Override
  public void initialize(ReservedWord constraintAnnotation) {
    reservedWord = constraintAnnotation.reservedWord();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !reservedWord.equalsIgnoreCase(value);
  }
}
