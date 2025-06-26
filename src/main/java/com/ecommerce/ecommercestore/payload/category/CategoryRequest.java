package com.ecommerce.ecommercestore.payload.category;

import com.ecommerce.ecommercestore.model.enums.CategoryName;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CategoryRequest {
    @NotNull(message = "Category name cannot be blank")
    private CategoryName name;
    private String description;
}


