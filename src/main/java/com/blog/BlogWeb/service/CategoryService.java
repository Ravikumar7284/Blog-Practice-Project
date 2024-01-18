package com.blog.BlogWeb.service;

import com.blog.BlogWeb.dto.CategoryDto;
import com.blog.BlogWeb.entity.Category;
import com.blog.BlogWeb.exception.ResourceNotFoundException;
import com.blog.BlogWeb.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository repository;

  @Autowired
  private ModelMapper modelMapper;

  public CategoryDto createCategory(CategoryDto categoryDto) {
    Category tempCategory = this.modelMapper.map(categoryDto, Category.class);
    Category newCategory = this.repository.save(tempCategory);
    return this.modelMapper.map(newCategory, CategoryDto.class);
  }

  public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
    Category tempCategory = this.repository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
    tempCategory.setTitle(categoryDto.getTitle());
    tempCategory.setDescription(categoryDto.getDescription());
    Category updatedCategory = this.repository.save(tempCategory);
    return this.modelMapper.map(updatedCategory, CategoryDto.class);
  }

  public void deleteCategory(Integer categoryId) {
    Category tempCategory = this.repository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
    this.repository.delete(tempCategory);
  }

  public CategoryDto getCategoryById(Integer categoryId) {
    Category tempCategory = this.repository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
    return this.modelMapper.map(tempCategory, CategoryDto.class);
  }

  public List<CategoryDto> getAllCategories() {
    List<Category> categoryList = this.repository.findAll();
    List<CategoryDto> categoryDtoList = categoryList.stream()
        .map(category -> this.modelMapper.map(category,
            CategoryDto.class)).collect(Collectors.toList());
    return categoryDtoList;
  }
}
