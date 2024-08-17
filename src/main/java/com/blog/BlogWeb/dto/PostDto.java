package com.blog.BlogWeb.dto;

import com.blog.BlogWeb.entity.Category;
import com.blog.BlogWeb.entity.Comment;
import com.blog.BlogWeb.entity.User;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PostDto {

  private Integer id;

  @NotBlank
  @Size(min = 3, message = "Title should ne more than 3 characters")
  private String title;

  private String content;

  private String imageName;

  private Date addedDate;

  private CategoryDto category;

  private UserDto user;

  private List<CommentDto> comment;

}
