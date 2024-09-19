package com.blog.BlogWeb.exception;


public class ApiException extends RuntimeException {
  public ApiException(String message) {
    super(message);
  }
}
