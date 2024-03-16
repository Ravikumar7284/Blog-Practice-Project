package com.blog.BlogWeb.dto;

import com.blog.BlogWeb.entity.Post;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PostResponse {

  private List<PostDto> content;
  private Integer pageNumber;
  private Integer pageSize;
  private Long totalElements;
  private Integer totalPages;
  private Boolean lastPage;
}
