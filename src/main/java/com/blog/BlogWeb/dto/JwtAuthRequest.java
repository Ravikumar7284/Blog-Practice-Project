package com.blog.BlogWeb.dto;

import lombok.Data;

@Data
public class JwtAuthRequest {

  private String email;
  private String password;

}
