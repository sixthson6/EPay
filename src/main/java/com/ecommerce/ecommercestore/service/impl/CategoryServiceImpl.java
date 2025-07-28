package com.ecommerce.ecommercestore.service.impl;

import com.ecommerce.ecommercestore.exception.ResourceNotFoundException;
import com.ecommerce.ecommercestore.model.Category;
import com.ecommerce.ecommercestore.model.Product;
import com.ecommerce.ecommercestore.payload.category.CategoryRequest;
import com.ecommerce.ecommercestore.payload.category.CategoryResponse;
import com.ecommerce.ecommercestore.repository.CategoryRepository;
import com.ecommerce.ecommercestore.repository.ProductRepository;
import com.ecommerce.ecommercestore.service.CategoryService;
import com.ecommerce.ecommercestore.service.ProductService;
import com.ecommerce.ecommercestore.service.mapper.CategoryMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, ProductRepository productRepository, @Lazy ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = categoryMapper.toCategory(categoryRequest);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(String categoryId, CategoryRequest categoryRequest) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        existingCategory.setName(categoryRequest.getName());
        existingCategory.setDescription(categoryRequest.getDescription());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toCategoryResponse(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Product> products = productRepository.findByCategory(category);
        for(Product product : products){
            productService.deleteProduct(product.getId());
        }

        categoryRepository.deleteById(categoryId);
    }
}
