package com.blog.BlogWeb.exception;


import com.blog.BlogWeb.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Response> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
    String message = exception.getMessage();
    Response response = new Response(message,false);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }
}
