package com.ecommerce.ecommercestore.payload.product;

import com.ecommerce.ecommercestore.model.enums.CategoryName;
import com.ecommerce.ecommercestore.payload.category.CategoryResponse;
import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Integer stockQuantity;
    private String imageUrl;
    private CategoryName category;

    public CategoryName setCategoryName(CategoryName category) {
        this.category = category;
        return this.category;
    }
}