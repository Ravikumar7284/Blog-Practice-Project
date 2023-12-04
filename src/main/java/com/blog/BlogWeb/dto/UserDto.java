package com.blog.BlogWeb.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {

  private Integer id;
  private String name;
  private String email;
  private String password;
  private String about;
}
