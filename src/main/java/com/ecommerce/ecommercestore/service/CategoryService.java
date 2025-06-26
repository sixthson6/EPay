package com.ecommerce.ecommercestore.service;

import com.ecommerce.ecommercestore.model.Category;
import com.ecommerce.ecommercestore.payload.category.CategoryRequest;
import com.ecommerce.ecommercestore.payload.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse getCategoryById(Long categoryId);
    List<CategoryResponse> getAllCategories();
    CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);
    void deleteCategory(Long categoryId);
    CategoryResponse mapToDTO(Category category);
    Category mapToEntity(CategoryRequest categoryRequest);
}
