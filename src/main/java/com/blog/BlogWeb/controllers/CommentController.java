package com.blog.BlogWeb.controllers;

import com.blog.BlogWeb.dto.CommentDto;
import com.blog.BlogWeb.dto.Response;
import com.blog.BlogWeb.entity.Comment;
import com.blog.BlogWeb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @PostMapping("/{postId}")
  public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
      @PathVariable("postId") Integer postId) {
    CommentDto tempComment = this.commentService.createComment(commentDto, postId);
    return new ResponseEntity<CommentDto>(tempComment, HttpStatus.CREATED);
  }

  @DeleteMapping("/{commentId}")
  public Response deleteComment(@PathVariable Integer commentId) {
    this.commentService.deleteComment(commentId);
    return new Response("comment is successfully deleted", true);
  }

}
