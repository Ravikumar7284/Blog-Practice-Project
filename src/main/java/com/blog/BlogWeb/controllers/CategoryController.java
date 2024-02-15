package com.blog.BlogWeb.controllers;

import com.blog.BlogWeb.dto.CategoryDto;
import com.blog.BlogWeb.dto.Response;
import com.blog.BlogWeb.dto.UserDto;
import com.blog.BlogWeb.service.CategoryService;
import java.util.List;
import javax.validation.Valid;
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
@RequestMapping("/api/categories")
public class CategoryController {

  @Autowired
  private CategoryService service;

  @PostMapping
  public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
    CategoryDto newCategoryDto = this.service.createCategory(categoryDto);
    return new ResponseEntity<>(newCategoryDto, HttpStatus.CREATED);
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
      @PathVariable Integer categoryId) {
    CategoryDto updatedCategory = this.service.updateCategory(categoryDto, categoryId);
    return ResponseEntity.ok(updatedCategory);
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<Response> deleteUser(@PathVariable Integer categoryId) {
    this.service.deleteCategory(categoryId);
    return new ResponseEntity<>(new Response("Category Deleted Successfully", true), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    return ResponseEntity.ok(this.service.getAllCategories());
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer categoryId) {
    return ResponseEntity.ok(this.service.getCategoryById(categoryId));
  }

}
