package com.blog.BlogWeb.service;

import com.blog.BlogWeb.dto.UserDto;
import com.blog.BlogWeb.entity.User;
import com.blog.BlogWeb.exception.ResourceNotFoundException;
import com.blog.BlogWeb.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private ModelMapper modelMapper;

  public UserDto createUser(UserDto userDto) {
    User user = this.dtoToUser(userDto);
    user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
    User savedUser = this.repository.save(user);
    return this.userToDto(savedUser);
  }

  public UserDto updateUser(UserDto userDto, Integer userId) {
    User user = this.repository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
    user.setAbout(userDto.getAbout());

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
    User user = this.modelMapper.map(userDto, User.class);
    return user;
  }

  private UserDto userToDto(User user) {
    UserDto userDto = this.modelMapper.map(user, UserDto.class);
    return userDto;
  }
}
