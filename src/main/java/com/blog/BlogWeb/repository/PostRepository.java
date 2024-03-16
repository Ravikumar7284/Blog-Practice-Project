package com.blog.BlogWeb.repository;

import com.blog.BlogWeb.entity.Category;
import com.blog.BlogWeb.entity.Post;
import com.blog.BlogWeb.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

  List<Post> findByUser(User user);

  List<Post> findByCategory(Category category);

  // TODO: 16-03-2024  create the query for search
//  @Query(value = "SELECT p FROM post p WHERE p.title =:title)",nativeQuery = true)
  List<Post> findByTitle(String title);
}
