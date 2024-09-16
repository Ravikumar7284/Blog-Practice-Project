package com.blog.BlogWeb.controllers;

import com.blog.BlogWeb.dto.UserDto;
import com.blog.BlogWeb.dto.Response;
import com.blog.BlogWeb.service.UserService;
import java.util.List;
import javax.validation.Valid;
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
  private UserService userService;

  @PostMapping
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
    UserDto newUserDto = this.userService.createUser(userDto);
    return new ResponseEntity<>(newUserDto, HttpStatus.CREATED);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
      @PathVariable Integer userId) {
    UserDto updatedUser = this.userService.updateUser(userDto, userId);
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Response> deleteUser(@PathVariable Integer userId) {
    this.userService.deleteUser(userId);
    return new ResponseEntity<>(new Response("User Deleted Successfully", true), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return ResponseEntity.ok(this.userService.getAllUsers());
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
    return ResponseEntity.ok(this.userService.getUserById(userId));
  }

  @PostMapping("/{userId}/roles/{roleId}")
  public ResponseEntity<UserDto> assignedRoleToUser(@PathVariable Integer userId,
      @PathVariable Integer roleId) {
    UserDto assignedUserDto = this.userService.assignRoleToUser(userId, roleId);
    return new ResponseEntity<UserDto>(assignedUserDto, HttpStatus.OK);
  }
}
