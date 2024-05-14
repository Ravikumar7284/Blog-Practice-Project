package com.blog.BlogWeb.controllers;

import com.blog.BlogWeb.config.Constants;
import com.blog.BlogWeb.dto.PostDto;
import com.blog.BlogWeb.dto.PostResponse;
import com.blog.BlogWeb.dto.Response;
import com.blog.BlogWeb.service.FileService;
import com.blog.BlogWeb.service.PostService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  @Autowired
  private PostService service;
  @Autowired
  private FileService fileService;

  @Value("${project.image}")
  private String path;

  @PostMapping("/users/{userId}/categories/{categoryId}")
  public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
      @PathVariable Integer userId, @PathVariable Integer categoryId) {
    PostDto createdPost = this.service.createPost(postDto, userId, categoryId);
    return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
      @RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
      @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) Integer pageSize) {
    PostResponse postResponse = this.service.getPostsByUser(userId, pageNumber, pageSize);
    return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
  }

  @GetMapping("/categories/{categoryId}")
  public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
      @RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
      @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) Integer pageSize) {
    PostResponse postResponse = this.service.getPostsByCategory(categoryId, pageNumber, pageSize);
    return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<PostResponse> getAllPosts(
      @RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
      @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) Integer pageSize,
      @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy,
      @RequestParam(value = "sortDirection", defaultValue = Constants.SORT_DIRECTION, required = false) String sortDir) {
    PostResponse postResponse = this.service.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
    return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
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

  @GetMapping("/search")
  public ResponseEntity<List<PostDto>> getPostByKeword(@RequestParam String title) {
    List<PostDto> posts = this.service.searchPost(title);
    return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
  }

  @PostMapping("/file/upload/{postId}")
  public ResponseEntity<PostDto> uploadFile(@RequestParam("file") MultipartFile file,
      @PathVariable("postId") Integer postId) throws IOException {
    PostDto postDto = this.service.getPostById(postId);
    String fileName = this.fileService.uploadImage(path, file);
    postDto.setImageName(fileName);
    PostDto updatePost = this.service.updatePost(postDto, postId);
    return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
  }

  @GetMapping("/file/{fileName}")
  public void downloadFile(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException{
    InputStream inputStream = this.fileService.getResource(path,fileName);
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(inputStream,response.getOutputStream());
  }


}

