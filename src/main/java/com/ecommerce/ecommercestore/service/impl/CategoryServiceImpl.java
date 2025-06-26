package com.ecommerce.ecommercestore.service.impl;

import com.ecommerce.ecommercestore.exception.EcommerceAPIException;
import com.ecommerce.ecommercestore.exception.ResourceNotFoundException;
import com.ecommerce.ecommercestore.model.Category;
import com.ecommerce.ecommercestore.model.enums.CategoryName;
import com.ecommerce.ecommercestore.payload.category.CategoryRequest;
import com.ecommerce.ecommercestore.payload.category.CategoryResponse;
import com.ecommerce.ecommercestore.repository.CategoryRepository;
import com.ecommerce.ecommercestore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.findByName(categoryRequest.getName()).isPresent()) {
            throw new EcommerceAPIException(HttpStatus.BAD_REQUEST, "Category with name '" + categoryRequest.getName() + "' already exists.");
        }

        Category category = Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .build();
        Category newCategory = categoryRepository.save(category);
        return modelMapper.map(newCategory, CategoryResponse.class);
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId.toString()));
        return modelMapper.map(category, CategoryResponse.class);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId.toString()));

        if (!category.getName().equals(categoryRequest.getName())) {
            if (categoryRepository.findByName(categoryRequest.getName()).isPresent()) {
                throw new EcommerceAPIException(HttpStatus.BAD_REQUEST, "Category with name '" + categoryRequest.getName() + "' already exists.");
            }
            category.setName(categoryRequest.getName());
        }
        category.setDescription(categoryRequest.getDescription());

        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryResponse.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId.toString()));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse mapToDTO(Category category) {
        return modelMapper.map(category, CategoryResponse.class);
    }

    @Override
    public Category mapToEntity(CategoryRequest categoryRequest) {
        return modelMapper.map(categoryRequest, Category.class);
    }
}