package com.blog.BlogWeb.controllers;

import com.blog.BlogWeb.dto.PostDto;
import com.blog.BlogWeb.dto.Response;
import com.blog.BlogWeb.dto.UserDto;
import com.blog.BlogWeb.entity.Post;
import com.blog.BlogWeb.service.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  @Autowired
  private PostService service;

  @PostMapping("/users/{userId}/categories/{categoryId}")
  public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
      @PathVariable Integer userId, @PathVariable Integer categoryId) {
    PostDto createdPost = this.service.createPost(postDto, userId, categoryId);
    return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
    List<PostDto> posts = this.service.getPostsByUser(userId);
    return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
  }

  @GetMapping("/categories/{categoryId}")
  public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
    List<PostDto> posts = this.service.getPostsByCategory(categoryId);
    return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<PostDto>> getAllPosts() {
    return ResponseEntity.ok(this.service.getAllPosts());
  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer postId) {
    return ResponseEntity.ok(this.service.getPostById(postId));
  }

  @DeleteMapping("/{postId}")
  public Response deletePost(@PathVariable Integer postId) {
    this.service.deletePost(postId);
    return new Response("Post is successfully deleted", true);
  }

  @PutMapping("/{postId}")
  public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
      @PathVariable Integer postId) {
    PostDto updatedPost = this.service.updatePost(postDto, postId);
    return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
  }

}
