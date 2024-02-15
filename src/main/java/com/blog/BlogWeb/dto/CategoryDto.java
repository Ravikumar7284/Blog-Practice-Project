package com.blog.BlogWeb.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CategoryDto {

  private Integer id;

  @NotBlank
  @Size(min = 3, message = "Title should ne more than 3 characters")
  private String title;

  @NotBlank
  @Size(min = 10, message = "Description should be more than 10 characters")
  private String description;
}
