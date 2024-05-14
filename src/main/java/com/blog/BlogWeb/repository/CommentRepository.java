package com.blog.BlogWeb.repository;

import com.blog.BlogWeb.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
