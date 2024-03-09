package com.blog.BlogWeb.service;

import com.blog.BlogWeb.dto.PostDto;
import com.blog.BlogWeb.entity.Category;
import com.blog.BlogWeb.entity.Post;
import com.blog.BlogWeb.entity.User;
import com.blog.BlogWeb.exception.ResourceNotFoundException;
import com.blog.BlogWeb.repository.CategoryRepository;
import com.blog.BlogWeb.repository.PostRepository;
import com.blog.BlogWeb.repository.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  @Autowired
  private PostRepository repository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ModelMapper modelMapper;

  public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
    Category category = this.categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));

    Post post = this.modelMapper.map(postDto, Post.class);
    post.setAddedDate(new Date());
    post.setUser(user);
    post.setCategory(category);

    Post newPost = this.repository.save(post);

    return this.modelMapper.map(newPost, PostDto.class);

  }

  public PostDto updatePost(PostDto postDto, Integer postId) {
    Post post = this.repository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
    post.setTitle(postDto.getTitle());
    post.setContent(postDto.getContent());
    post.setImageName(post.getImageName());

    Post updatedPost = this.repository.save(post);

    return this.modelMapper.map(updatedPost, PostDto.class);
  }

  public void deletePost(Integer postId) {
    Post post = this.repository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
    this.repository.delete(post);
  }

  public List<PostDto> getAllPosts() {
    List<Post> posts = this.repository.findAll();
    List<PostDto> postDtos = posts.stream().map((post -> this.modelMapper.map(post, PostDto.class)))
        .collect(Collectors.toList());
    return postDtos;
  }

  public PostDto getPostById(Integer postId) {
    Post tempPost = this.repository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));
    return this.modelMapper.map(tempPost, PostDto.class);
  }

  public List<PostDto> getPostsByCategory(Integer categoryId) {
    Category category = this.categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));
    List<Post> posts = this.repository.findByCategory(category);
    List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
        .collect(Collectors.toList());

    return postDtos;
  }

  public List<PostDto> getPostsByUser(Integer userId) {
    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
    List<Post> posts = this.repository.findByUser(user);
    List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
        .collect(Collectors.toList());

    return postDtos;
  }

//  List<PostDto> searchPost(String keyword);
}
