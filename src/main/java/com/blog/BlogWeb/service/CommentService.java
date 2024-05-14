package com.blog.BlogWeb.service;

import com.blog.BlogWeb.dto.CommentDto;
import com.blog.BlogWeb.entity.Comment;
import com.blog.BlogWeb.entity.Post;
import com.blog.BlogWeb.exception.ResourceNotFoundException;
import com.blog.BlogWeb.repository.CommentRepository;
import com.blog.BlogWeb.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private ModelMapper modelMapper;

  public CommentDto createComment(CommentDto commentDto, Integer postId) {
    Post post = this.postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
    Comment comment = this.modelMapper.map(commentDto, Comment.class);
    comment.setPost(post);
    Comment savedComment = this.commentRepository.save(comment);
    return this.modelMapper.map(savedComment, CommentDto.class);
  }

  public void deleteComment(Integer commentId) {
    Comment comment = this.commentRepository.findById(commentId)
        .orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
    this.commentRepository.delete(comment);
  }

}
