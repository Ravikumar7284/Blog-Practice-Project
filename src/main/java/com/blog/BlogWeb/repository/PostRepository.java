package com.blog.BlogWeb.repository;

import com.blog.BlogWeb.entity.Category;
import com.blog.BlogWeb.entity.Post;
import com.blog.BlogWeb.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {

  List<Post> findByUser(User user);

  List<Post> findByCategory(Category category);

  Page<Post> findByUser(User user, Pageable pageable);

  Page<Post> findByCategory(Category category, Pageable pageable);

  @Query(value = "SELECT * FROM post WHERE post_title LIKE %:title%", nativeQuery = true)
  List<Post> findByTitle(@Param("title") String title);
}
