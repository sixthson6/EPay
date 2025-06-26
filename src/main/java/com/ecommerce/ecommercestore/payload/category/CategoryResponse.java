package com.ecommerce.ecommercestore.payload.category;

import com.ecommerce.ecommercestore.model.enums.CategoryName;
import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private CategoryName name;
    private String description;
}