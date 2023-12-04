package com.blog.BlogWeb.service;

import com.blog.BlogWeb.dto.UserDto;
import com.blog.BlogWeb.entity.User;
import com.blog.BlogWeb.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserRepository repository;

 public UserDto createUser(UserDto userDto){
    User user = this.dtoToUser(userDto);
    User savedUser = this.repository.save(user);
    return this.userToDto(savedUser);
  }

 public UserDto updateUser(UserDto userDto, Integer userId){
    return null;
  }

 public UserDto getUserById(Integer userId){
    return null;
  }

 public List<UserDto> getAllUsers(){
    return null;
  }

 public void deleteUser(Integer userId){

  }

  private User dtoToUser(UserDto userDto){
    User user = new User();
    user.setId(userDto.getId());
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(userDto.getPassword());
    user.setAbout(userDto.getAbout());
    return user;
 }

 private UserDto userToDto(User user){
   UserDto userDto = new UserDto();
   userDto.setId(user.getId());
   userDto.setName(user.getName());
   userDto.setEmail(user.getEmail());
   userDto.setPassword(user.getPassword());
   userDto.setAbout(user.getAbout());
   return userDto;
 }
}
