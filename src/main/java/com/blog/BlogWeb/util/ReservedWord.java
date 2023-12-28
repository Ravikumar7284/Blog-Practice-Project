package com.blog.BlogWeb.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ReservedWordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReservedWord {

  String reservedWord();
  String message() default "\"Password\" is a bad password";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
