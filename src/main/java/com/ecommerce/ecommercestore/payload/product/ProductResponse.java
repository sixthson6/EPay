package com.ecommerce.ecommercestore.payload.product;

import com.ecommerce.ecommercestore.model.Category;
import com.ecommerce.ecommercestore.payload.category.CategoryResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private String brand;
    private Integer stockQuantity;
    private String imageUrl;
    private Category category;
}