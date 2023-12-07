package com.blog.BlogWeb.controllers;

import com.blog.BlogWeb.dto.UserDto;
import com.blog.BlogWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService service;

  @PostMapping
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
    UserDto newUserDto = this.service.createUser(userDto);
    return new ResponseEntity<>(newUserDto, HttpStatus.CREATED);
  }
}
