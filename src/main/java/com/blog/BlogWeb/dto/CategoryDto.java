package com.blog.BlogWeb.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CategoryDto {

  private Integer id;
  private String title;
  private String description;
}
