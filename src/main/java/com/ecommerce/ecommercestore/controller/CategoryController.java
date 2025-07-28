package com.ecommerce.ecommercestore.controller;

import com.ecommerce.ecommercestore.payload.category.CategoryRequest;
import com.ecommerce.ecommercestore.payload.category.CategoryResponse;
import com.ecommerce.ecommercestore.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse createdCategory = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") String categoryId) {
        CategoryResponse category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") String categoryId, @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
