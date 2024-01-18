package com.blog.BlogWeb.repository;

import com.blog.BlogWeb.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
