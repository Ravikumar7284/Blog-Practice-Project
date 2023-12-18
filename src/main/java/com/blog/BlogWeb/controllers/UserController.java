package com.blog.BlogWeb.controllers;

import com.blog.BlogWeb.dto.UserDto;
import com.blog.BlogWeb.dto.Response;
import com.blog.BlogWeb.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService service;

  @PostMapping
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
    UserDto newUserDto = this.service.createUser(userDto);
    return new ResponseEntity<>(newUserDto, HttpStatus.CREATED);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,
      @PathVariable Integer userId) {
    UserDto updatedUser = this.service.updateUser(userDto, userId);
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Response> deleteUser(@PathVariable Integer userId){
    this.service.deleteUser(userId);
    return new ResponseEntity<>(new Response("User Deleted Successfully", true), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<UserDto>> getAllUsers(){
    return ResponseEntity.ok(this.service.getAllUsers());
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
    return ResponseEntity.ok(this.service.getUserById(userId));
  }
}
