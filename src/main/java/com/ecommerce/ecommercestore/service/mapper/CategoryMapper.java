package com.ecommerce.ecommercestore.service.mapper;

import com.ecommerce.ecommercestore.model.Category;
import com.ecommerce.ecommercestore.payload.category.CategoryRequest;
import com.ecommerce.ecommercestore.payload.category.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toCategoryResponse(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public Category toCategory(CategoryRequest request) {
        if (request == null) {
            return null;
        }
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return category;
    }
}
