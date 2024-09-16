package com.blog.BlogWeb.service;

import com.blog.BlogWeb.dto.UserDto;
import com.blog.BlogWeb.entity.Role;
import com.blog.BlogWeb.entity.User;
import com.blog.BlogWeb.exception.ResourceNotFoundException;
import com.blog.BlogWeb.repository.RoleRepository;
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
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private ModelMapper modelMapper;

  public UserDto createUser(UserDto userDto) {
    User user = this.dtoToUser(userDto);
    user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
    User savedUser = this.userRepository.save(user);
    return this.userToDto(savedUser);
  }

  public UserDto updateUser(UserDto userDto, Integer userId) {
    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
    user.setAbout(userDto.getAbout());

    User updatedUser = this.userRepository.save(user);
    UserDto newUserDto = this.userToDto(updatedUser);
    return newUserDto;
  }

  public UserDto getUserById(Integer userId) {
    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

    UserDto userDto = this.userToDto(user);
    return userDto;
  }

  public List<UserDto> getAllUsers() {
    List<User> users = this.userRepository.findAll();
    List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user))
        .collect(Collectors.toList());
    return userDtos;
  }

  public void deleteUser(Integer userId) {
    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
    this.userRepository.delete(user);
  }

  public UserDto assignRoleToUser(Integer userId, Integer roleId) {
    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
    Role role = this.roleRepository.findById(roleId)
        .orElseThrow(() -> new ResourceNotFoundException("Role", "Id", roleId));
    user.getRole().add(role);
    User assignedUser = this.userRepository.save(user);
    return this.userToDto(assignedUser);
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
