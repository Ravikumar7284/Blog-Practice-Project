package com.blog.BlogWeb.dto;

import com.blog.BlogWeb.util.ReservedWord;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {

  private Integer id;

  @NotEmpty
  @Size(min = 4, max = 50, message = "Username must be min of 4 and max to 50 characters")
  private String name;

  @Email(message = "Email is not valid")
  private String email;

  @NotEmpty
  @Size(min = 4, max = 50, message = "Password must be min of 4 and max to 50 characters")
  @ReservedWord(reservedWord = "password")
  private String password;

  @NotEmpty
  private String about;
}
