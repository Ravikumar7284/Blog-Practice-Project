package com.blog.BlogWeb.service;

import com.blog.BlogWeb.dto.UserDto;
import com.blog.BlogWeb.entity.User;
import com.blog.BlogWeb.exception.ResourceNotFoundException;
import com.blog.BlogWeb.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public UserDto createUser(UserDto userDto) {
    User user = this.dtoToUser(userDto);
    User savedUser = this.repository.save(user);
    return this.userToDto(savedUser);
  }

  public UserDto updateUser(UserDto userDto, Integer userId) {
    User user = this.repository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(user.getPassword());
    user.setAbout(user.getAbout());

    User updatedUser = this.repository.save(user);
    UserDto newUserDto = this.userToDto(updatedUser);
    return newUserDto;
  }

  public UserDto getUserById(Integer userId) {
    User user = this.repository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

    UserDto userDto = this.userToDto(user);
    return userDto;
  }

  public List<UserDto> getAllUsers() {
    List<User> users = this.repository.findAll();
    List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user))
        .collect(Collectors.toList());
    return userDtos;
  }

  public void deleteUser(Integer userId) {
    User user = this.repository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
    this.repository.delete(user);
  }

  private User dtoToUser(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(userDto.getPassword());
    user.setAbout(userDto.getAbout());
    return user;
  }

  private UserDto userToDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setEmail(user.getEmail());
    userDto.setPassword(user.getPassword());
    userDto.setAbout(user.getAbout());
    return userDto;
  }
}
