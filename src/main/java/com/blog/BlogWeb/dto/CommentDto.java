package com.blog.BlogWeb.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CommentDto {

  private Integer id;
  private String content;
}
